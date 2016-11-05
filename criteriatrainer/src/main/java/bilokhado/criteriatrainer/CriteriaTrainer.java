package bilokhado.criteriatrainer;

import bilokhado.criteriatrainer.service.CriteriaCaseService;
import bilokhado.criteriatrainer.service.provider.CriteriaCaseServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Objects;

/**
 * Created by dimon on 05.11.16.
 */
public class CriteriaTrainer {

    private static final Logger LOG = LoggerFactory.getLogger(CriteriaTrainer.class);

    public static void main(String[] commandLine) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("world");
            em = emf.createEntityManager();
            List<CriteriaCaseService> serviceList = CriteriaCaseServiceProvider.getInstanse().getImplementations();
            for (CriteriaCaseService service : serviceList) {
                List<? extends Object> results = service.getResultWithCriteria(em);
                System.out.println(results);
            }
        } finally {
            if (em != null)
                em.close();
            if (emf != null)
                emf.close();
        }
    }
}
