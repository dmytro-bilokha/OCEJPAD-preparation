package bilokhado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HelloJpaServlet", urlPatterns = {"/hello"})
public class HelloJpaServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(HelloJpaServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("In doPost method");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOG.info("In doGet method");
        PrintWriter out = response.getWriter();
        out.println("Hello from Jpa Servlet!");
    }

}
