package bilokhado.criteriatrainer.service.implementation;

import bilokhado.criteriatrainer.model.entity.City;
import bilokhado.criteriatrainer.model.entity.City_;
import bilokhado.criteriatrainer.model.entity.Country;
import bilokhado.criteriatrainer.model.entity.Country_;
import bilokhado.criteriatrainer.service.CriteriaCaseService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Criteria case to select all countries which have big cities
 */
public class CountryWhichHasBigCityCase implements CriteriaCaseService {

    /**
     * @inheritDoc
     */
    @Override
    public List<Country> getResultWithCriteria(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Country> query = cb.createQuery(Country.class);
        Root<City> rootCity = query.from(City.class);
        Root<Country> rootCountry = query.from(Country.class);
        rootCountry.fetch(Country_.cities);
        query.select(rootCountry)
                .distinct(true)
                .where(
                        cb.and(
                                cb.gt(rootCity.get(City_.population), 3000000)
                                , cb.equal(rootCity.get(City_.country), rootCountry)
                        )
                );
        return em.createQuery(query).getResultList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getJpqlEquivalentString() {
        return "SELECT DISTINCT ct.country FROM City ct JOIN FETCH ct.country.cities WHERE ct.population > 3000000";
    }

}
