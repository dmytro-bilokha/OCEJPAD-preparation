package bilokhado.criteriatrainer.service.implementation;

import bilokhado.criteriatrainer.model.entity.City;
import bilokhado.criteriatrainer.model.entity.City_;
import bilokhado.criteriatrainer.model.entity.Country;
import bilokhado.criteriatrainer.service.CriteriaCaseService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

/**
 * Criteria case to select country which has the biggest city
 */
public class CountryWithBiggestCityCase implements CriteriaCaseService {

    @Override
    public List<Country> getResultWithCriteria(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Country> query = cb.createQuery(Country.class);
        Root<City> rootCity = query.from(City.class);
        Subquery<Integer> subquery = query.subquery(Integer.class);
        Root<City> subrootCity = subquery.from(City.class);
        subquery.select(subrootCity.get(City_.population));
        query.select(rootCity.get(City_.country)).where(cb.ge(rootCity.get(City_.population), cb.all(subquery)));
        return em.createQuery(query).getResultList();
    }

    @Override
    public String getJpqlEquivalentString() {
        return "SELECT c.country FROM City c WHERE c.population >= ALL (SELECT ct.population FROM City ct)";
    }
}
