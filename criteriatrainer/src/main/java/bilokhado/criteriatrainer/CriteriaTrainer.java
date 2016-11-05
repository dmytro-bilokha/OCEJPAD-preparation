package bilokhado.criteriatrainer;

import bilokhado.criteriatrainer.service.CriteriaCaseService;
import bilokhado.criteriatrainer.service.provider.CriteriaCaseServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * The main class of the application.
 */
public class CriteriaTrainer {

    private static final Logger LOG = LoggerFactory.getLogger(CriteriaTrainer.class);

    /**
     * Loads all criteria querying services and runs them one by one. Print every result set.
     * If JPQL and criteria result sets differs, the method prints both.
     * @param commandLine ignored for now
     */
    @SuppressWarnings("unchecked") //Because we use EntityManager.createQuery(String query)
    public static void main(String[] commandLine) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("world");
            em = emf.createEntityManager();
            List<CriteriaCaseService> serviceList = CriteriaCaseServiceProvider.getInstanse().getImplementations();
            for (CriteriaCaseService service : serviceList) {
                try {
                    List<? extends Object> criteriaResult = service.getResultWithCriteria(em);
                    String jpqlQuery = service.getJpqlEquivalentString();
                    List<? extends Object> jpqlResult = em.createQuery(jpqlQuery).getResultList();
                    if (isResultsEqual(criteriaResult, jpqlResult)) {
                        System.out.println("Criteria results for equivalent JPQL query '" + jpqlQuery + "':");
                        printResult(criteriaResult);
                    } else {
                        System.out.println("Criteria and JPQL results are not equal!");
                        System.out.println("Criteria result:");
                        printResult(criteriaResult);
                        System.out.println("JPQL result for query '" + jpqlQuery + "' :");
                        printResult(jpqlResult);
                    }
                } catch (Exception ex) {
                    System.out.println("During testing criteria query object with JPQL string '"
                        + service.getJpqlEquivalentString() + "' exception happened");
                    LOG.error("During testing criteria query object with JPQL string '"
                            + service.getJpqlEquivalentString() + "' exception happened", ex);
                }
            }
        } finally {
            if (em != null)
                em.close();
            if (emf != null)
                emf.close();
        }
    }

    private static boolean isResultsEqual(List<? extends Object> firstResult, List<? extends Object> secondResult) {
        if (firstResult.size() != secondResult.size())
            return false;
        for (Object resultItem : firstResult) {
            if (!secondResult.contains(resultItem)) {
                return false;
            }
        }
        return true;
    }

    private static void printResult(List<? extends Object> resultList) {
        for (int i = 0; i < resultList.size(); i++) {
            Object result = resultList.get(i);
            System.out.println(String.format("%3d: %s", i, result));
        }
    }

}
