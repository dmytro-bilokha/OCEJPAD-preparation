package bilokhado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HelloJpaServlet", urlPatterns = {"/hello"})
public class HelloJpaServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(HelloJpaServlet.class);
    private static final String OUTPUT_START = "<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"
        + " 'http://www.w3.org/TR/html4/loose.dtd'>"
            + "<html lang='en'>"
            + "<head><title>Hello JPA EE</title></head>"
            + "<body><table><thead><tr><th>Id</th><th>First Name</th><th>Last Name</th></thead>"
            + "<tbody>";
    private static final String OUTPUT_END = "</tbody></table></body></html>";
    private static final String OUTPUT_LINE_TEMPLATE = "<tr><th>%d</th><th>%s</th><th>%s</th></tr>";

    @EJB
    private EmployeeService employeeService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("In doPost method");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(OUTPUT_START);
        employeeService.findAllEmployees().forEach(emp -> out.println(String.format(OUTPUT_LINE_TEMPLATE
                , emp.getId(), emp.getFirstName(), emp.getLastName())));
        out.println(OUTPUT_END);
    }

}
