package bilokhado.transactionexplorer.web;

import bilokhado.transactionexplorer.service.implementation.BookIdPoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ExplorerServlet", urlPatterns = {"/"})
public class ExplorerServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ExplorerServlet.class);
    private static final String OUTPUT_START = "<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"
        + " 'http://www.w3.org/TR/html4/loose.dtd'>"
            + "<html lang='en'>"
            + "<head><title>Transaction Explorer</title></head>"
            + "<body><table><thead><tr><th>Id</th><th>First Name</th><th>Last Name</th></thead>"
            + "<tbody>";
    private static final String OUTPUT_END = "</tbody></table></body></html>";
    private static final String OUTPUT_LINE_TEMPLATE = "<tr><th>%d</th><th></th><th></th></tr>";

    @EJB
    private BookIdPoolService poolService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(OUTPUT_START);
        int freeId = poolService.bookIdPool(1);
        out.println(String.format(OUTPUT_LINE_TEMPLATE, freeId));
        out.println(OUTPUT_END);
    }

}
