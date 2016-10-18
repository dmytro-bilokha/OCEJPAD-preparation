package bilokhado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HelloJpa {

    private static final Logger logger = LoggerFactory.getLogger(HelloJpa.class);

    public static void main(String[] cmdLineParams) {

        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("hellojpa-se");
            em = emf.createEntityManager();
            Employee employee = new Employee();
            employee.setId(1);
            employee.setFirstName("Test1");
            employee.setLastName("Test2");
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
        } finally {
            if (em != null)
                em.close();
            if (emf != null)
                emf.close();
        }
    }

}
