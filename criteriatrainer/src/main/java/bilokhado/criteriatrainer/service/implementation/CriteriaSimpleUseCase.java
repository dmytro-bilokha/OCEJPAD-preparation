package bilokhado.criteriatrainer.service.implementation;

import bilokhado.criteriatrainer.model.entity.Country;
import bilokhado.criteriatrainer.service.CriteriaCaseService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

/**
 * Simple implementation of the CriteriaCaseService, just to test if everything works as expected
 */
public class CriteriaSimpleUseCase implements CriteriaCaseService {

    /**
     * Queries database for the list of cities in Ukraine
     * @param em JPA entity manager
     * @return list of collection of cities
     */
    @Override
    public List<Collection> getResultWithCriteria(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Collection> query = cb.createQuery(Collection.class);
        Root<Country> root = query.from(Country.class);
        query.select(root.get("cities")).where(cb.equal(root.get("code"), "UKR"));
        return em.createQuery(query).getResultList();
    }

    /**
     * Returns JPQL query string to get cities of Ukraine
     * @return JPQL query string
     */
    @Override
    public String getJpqlEquivalentString() {
        return "SELECT c.cities FROM Country c WHERE c.code='UKR'";
    }

}
