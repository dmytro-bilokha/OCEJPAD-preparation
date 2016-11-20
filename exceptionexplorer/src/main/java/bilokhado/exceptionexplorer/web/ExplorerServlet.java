package bilokhado.exceptionexplorer.web;

import bilokhado.exceptionexplorer.service.TransactionEjb;
import bilokhado.exceptionexplorer.service.implementation.BookIdPoolService;
import bilokhado.exceptionexplorer.service.implementation.TransactionMandatoryEjb;
import bilokhado.exceptionexplorer.service.implementation.TransactionNeverEjb;
import bilokhado.exceptionexplorer.service.implementation.TransactionNotSupportedEjb;
import bilokhado.exceptionexplorer.service.implementation.TransactionRequiredEjb;
import bilokhado.exceptionexplorer.service.implementation.TransactionRequiresNewEjb;
import bilokhado.exceptionexplorer.service.implementation.TransactionSupportsEjb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@WebServlet(name = "ExplorerServlet", urlPatterns = {"/explore"})
public class ExplorerServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(ExplorerServlet.class);
    private static final String OUTPUT_START = "<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"
        + " 'http://www.w3.org/TR/html4/loose.dtd'>"
            + "<html lang='en'>"
            + "<head><title>Exception Explorer</title>"
            + "<link rel='stylesheet' type='text/css' href='resources/table.css' title='Style'></head><body>";
    private static final String OUTPUT_END = "</body></html>";

    @Resource(mappedName = "jdbc/h2mem")
    DataSource dataSource;


    @EJB  //Sevice to book items' id
    private BookIdPoolService poolService;

    //Discovered ejbs injection
    @EJB
    private TransactionRequiredEjb transactionRequiredEjb;

    @EJB
    private TransactionRequiresNewEjb transactionRequiresNewEjb;

    @EJB
    private TransactionMandatoryEjb transactionMandatoryEjb;

    @EJB
    private TransactionSupportsEjb transactionSupportsEjb;

    @EJB
    private TransactionNotSupportedEjb transactionNotSupportedEjb;

    @EJB
    private TransactionNeverEjb transactionNeverEjb;
    //End of discovered ejbs injection

    private TransactionEjb[] callersArray, calleesArray;

    @Override
    public void init() {
        callersArray = new TransactionEjb[]{
                transactionRequiredEjb
                , transactionRequiresNewEjb
                , transactionSupportsEjb
                , transactionNotSupportedEjb
                , transactionNeverEjb
        };
        calleesArray = new TransactionEjb[]{
                transactionRequiredEjb
                , transactionRequiresNewEjb
        };
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(OUTPUT_START);
        for (TransactionEjb caller : callersArray) {
            for (TransactionEjb callee : calleesArray) {
                Exception[] exceptionsToTest = new Exception[]{
                        new RuntimeException()
                        , new Exception()
                };
                out.print("<table><thead><tr><th>Exception thrown</th><th>Exception got</th><th>");
                out.print(caller.getType());
                out.print(" item persisted</th><th>");
                out.print(callee.getType());
                out.print(" item persisted</th><th>");
                out.print(caller.getType());
                out.print(" counter</th><th>");
                out.print(callee.getType());
                out.println(" counter</th></tr></thead><tbody>");
                for (Exception ex : exceptionsToTest) {
                    String exceptionSentName = ex.getClass().getSimpleName();
                    int callerCounterBefore = poolService.getEjbCounter(caller.getType());
                    int calleeCounterBefore = poolService.getEjbCounter(callee.getType());
                    int poolBegin = poolService.bookIdPool(2);
                    Optional<Exception> exceptionGot = caller.persistItemAndCallNext(poolBegin, ex, callee);
                    String exceptionGotName = exceptionGot.isPresent() ? exceptionGot.get().getClass().getSimpleName()
                            : "-";
                    int callerCounterAfter = poolService.getEjbCounter(caller.getType());
                    int calleeCounterAfter = poolService.getEjbCounter(callee.getType());
                    out.print("<tr><td>");
                    out.print(exceptionSentName);
                    out.print("</td><td>");
                    out.print(exceptionGotName);
                    out.print("</td><td>");
                    boolean[] itemPersisted = checkItemsVisible(poolBegin, poolBegin+1);
                    for (boolean isItemSaved : itemPersisted) {
                        if (isItemSaved)
                            out.print("+</td><td>");
                        else
                            out.print("-</td><td>");
                    }
                    out.print(callerCounterBefore);
                    out.print('/');
                    out.print(callerCounterAfter);
                    out.print("</td><td>");
                    out.print(calleeCounterBefore);
                    out.print('/');
                    out.print(calleeCounterAfter);
                    out.print("</td></tr>");
                }
                out.println("</tbody></table><br/>");
            }
        }
        out.println(OUTPUT_END);
    }

    private boolean[] checkItemsVisible(int startId, int endId) {
        boolean[] checkResult = new boolean[endId-startId+1];
        try (Connection con = dataSource.getConnection();
             PreparedStatement statement = con.prepareStatement("SELECT id FROM item WHERE id=?");) {
            for (int i = startId, j = 0; i < endId; i++, j++) {
                statement.setInt(1, i);
                try (ResultSet resultSet = statement.executeQuery();) {
                    if (resultSet.next())
                        checkResult[j] = true;
                    else
                        checkResult[i] = false;
                } catch (Exception ex) {
                    LOG.error("Exception during working with result set", ex);
                }
            }
        } catch (Exception ex) {
            LOG.error("Unable to get connection and prepare statement", ex);
        }
        return checkResult;
    }

}
