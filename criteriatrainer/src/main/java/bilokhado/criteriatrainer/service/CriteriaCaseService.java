package bilokhado.criteriatrainer.service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Interface for the service which queries database using JPA Criteria API
 */
public interface CriteriaCaseService {

    /**
     * Queries database with JPA Criteria API and returns the result set
     * @param em JPA entity manager
     * @return list of query results
     */
    public List<? extends Object> getResultWithCriteria(EntityManager em);

    /**
     * Returns string representing the JPQL query to get the same result set, as with Criteria API
     * @return JPQL query string
     */
    public String getJpqlEquivalentString();

}
