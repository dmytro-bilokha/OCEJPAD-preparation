package bilokhado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloJpa {

    private static final Logger logger = LoggerFactory.getLogger(HelloJpa.class);

    public static void main(String[] cmdLineParams) {
        System.out.println("Hello World");
        logger.info("Hello World logged");
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.
                    getConnection("jdbc:h2:~/test", "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // add application code here
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
