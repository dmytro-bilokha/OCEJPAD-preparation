package bilokhado.transactionexplorer.web;

import bilokhado.transactionexplorer.service.ChainLink;
import bilokhado.transactionexplorer.service.implementation.BookIdPoolService;
import bilokhado.transactionexplorer.service.implementation.TransactionMandatoryChainLink;
import bilokhado.transactionexplorer.service.implementation.TransactionRequiredChainLink;
import bilokhado.transactionexplorer.service.implementation.TransactionRequiresNewChainLink;
import bilokhado.transactionexplorer.service.implementation.TransactionSupportsChainLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ExplorerServlet", urlPatterns = {"/explore"})
public class ExplorerServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ExplorerServlet.class);
    private static final String OUTPUT_START = "<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"
        + " 'http://www.w3.org/TR/html4/loose.dtd'>"
            + "<html lang='en'>"
            + "<head><title>Transaction Explorer</title>"
            + "<link rel='stylesheet' type='text/css' href='resources/table.css' title='Style'></head><body>";
    private static final String OUTPUT_END = "</body></html>";

    @EJB
    private BookIdPoolService poolService;

    @EJB
    private TransactionRequiredChainLink transactionRequiredChainLink;

    @EJB
    private TransactionRequiresNewChainLink transactionRequiresNewChainLink;

    @EJB
    private TransactionMandatoryChainLink transactionMandatoryChainLink;

    @EJB
    private TransactionSupportsChainLink transactionSupportsChainLink;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(OUTPUT_START);
        for (List<ChainLink> exploreCase : buildTestBeansMatrix()) {
            int numOfBeans = exploreCase.size();
            int freeId = poolService.bookIdPool(numOfBeans);
            String[][] visibilityMatrix = new String[numOfBeans][];
            try {
                exploreCase.get(0).processChain(freeId, 0, exploreCase, visibilityMatrix);
            } catch (Exception ex) {
                String chainTitle = exploreCase.stream().map(ChainLink::getType).collect(Collectors.joining(","));
                LOG.error("During processing chain {} got exception", chainTitle, ex);
                out.print("<p> During processing chain ");
                out.print(chainTitle);
                out.print(" got exception ");
                out.print(ex);
                out.print("</p>");
                continue;
            }
            //Generate table header
            out.print("<table><thead><tr><th>Bean(");
            out.print(freeId);
            out.print(")</th>");
            for (ChainLink bean : exploreCase) {
                out.print(String.format("<th>%s</th>", bean.getType()));
            }
            out.println("</tr></thead><tbody>");
            //Generate table body
            for (int i = 0; i < numOfBeans; i++) {
                out.print(String.format("<tr><td>%s</td>", exploreCase.get(i).getType()));
                for (String visibility : visibilityMatrix[i]) {
                    out.print(String.format("<td>%s</td>", visibility));
                }
                out.println("</tr>");
            }
            out.println("</tbody></table><br/>");
        }
        out.println(OUTPUT_END);
    }

    private List<List<ChainLink>> buildTestBeansMatrix() {
        ChainLink[] allBeans = new ChainLink[]{
                transactionRequiredChainLink
                , transactionRequiresNewChainLink
                , transactionMandatoryChainLink
                , transactionSupportsChainLink};
        List<List<ChainLink>> result = new ArrayList<>();
        for (ChainLink first : allBeans) {
            for (ChainLink second : allBeans) {
                for (ChainLink third : allBeans) {
                    result.add(Arrays.asList(first, second, third));
                }
            }
        }
        return result;
    }

}
