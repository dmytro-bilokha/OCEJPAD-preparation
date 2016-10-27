package bilokhado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;
import java.io.Console;
import java.util.Collections;
import java.util.List;

public class JpqlTrainer {

    private static final Logger LOG = LoggerFactory.getLogger(JpqlTrainer.class);

    public static void main(String[] cmdLineParams) {
        Console console = System.console();
        if (console == null) {
            LOG.error("Unable to get console");
            return;
        }
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("jpqltrainer");
            em = emf.createEntityManager();
            console.writer().println("Welcome to JPQL Trainer. To exit enter empty query");
            while (true) {
                console.flush();
                String test = console.readLine("JPQL> ");
                if (test.trim().isEmpty())
                    break;
                //em.getTransaction().begin();
                List results = Collections.EMPTY_LIST;
                try {
                    results = em.createQuery(test).getResultList();
                } catch (Exception ex) {
                    LOG.error("Exception during quering EntityManager", ex);
                }
                if (results.isEmpty()) {
                    console.writer().println("Got empty result list");
                } else {
                    console.writer().println("Results:");
                    for (int i = 0; i < results.size(); i++) {
                        console.writer().println(i +": " + results.get(i));
                    }
                }
            }
        } finally {
            if (em != null)
                em.close();
            if (emf != null)
                emf.close();

        }
    }

}
