package bilokhado.criteriatrainer.service.implementation;

import bilokhado.criteriatrainer.model.entity.Country;
import bilokhado.criteriatrainer.model.entity.Country_;
import bilokhado.criteriatrainer.model.entity.Language;
import bilokhado.criteriatrainer.model.entity.Language_;
import bilokhado.criteriatrainer.service.CriteriaCaseService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Case to discover aggregate function with join clause and math calculations
 */
public class JoinAndAggregateCase implements CriteriaCaseService {

    @Override
    public List<? extends Object> getResultWithCriteria(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Country> rootCountry = query.from(Country.class);
        Root<Language> rootLanguage = query.from(Language.class);
        Expression joinOn = cb.equal(rootCountry, rootLanguage.get(Language_.country));
        Expression languageIsEnglish = cb.equal(rootLanguage.get(Language_.name), "English");
        query
                .select(cb.array(
                        rootCountry.get(Country_.continent)
                        , cb.sum(
                                cb.quot(
                                        cb.prod(
                                                rootCountry.get(Country_.population)
                                                , rootLanguage.get(Language_.percentage))
                                        , 100))))
                .where(cb.and(joinOn, languageIsEnglish))
                .groupBy(rootCountry.get(Country_.continent));
        return em.createQuery(query).getResultList();
    }

    @Override
    public String getJpqlEquivalentString() {
        return "SELECT c.continent, SUM(c.population * l.percentage / 100)"
        + " FROM Country c INNER JOIN Language l ON c.code=l.country.code"
        + " WHERE l.name='English' GROUP BY c.continent";
    }

}
