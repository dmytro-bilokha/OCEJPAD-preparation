package bilokhado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HelloJpa {

    private static final Logger logger = LoggerFactory.getLogger(HelloJpa.class);

    public static void main(String[] cmdLineParams) {
        System.out.println("Hello World");
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hellojpa-se");
        EntityManager em = emf.createEntityManager();
        logger.info("Hello World logged");
        em.close();
        emf.close();
    }

}
