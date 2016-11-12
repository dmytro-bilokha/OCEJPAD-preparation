package bilokhado.criteriatrainer.service.implementation;

import bilokhado.criteriatrainer.model.entity.City;
import bilokhado.criteriatrainer.model.entity.City_;
import bilokhado.criteriatrainer.model.entity.Language;
import bilokhado.criteriatrainer.model.entity.Language_;
import bilokhado.criteriatrainer.service.CriteriaCaseService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates the case when we need to obtain all official languages for countries to whom cities from the list belong
 */
public class OfficialLanguageInCityCase implements CriteriaCaseService {

    /**
     * @inheritDoc
     */
    @Override
    public List<String> getResultWithCriteria(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> query = cb.createQuery(String.class);
        Root<City> rootCity = query.from(City.class);
        Root<Language> rootLanguage = query.from(Language.class);
        Expression joinOn = cb.equal(rootCity.get(City_.country), rootLanguage.get(Language_.country));
        Expression cityIn = rootCity.get(City_.name).in(Arrays.asList("Kyiv", "London", "Washington"));
        Expression languageOfficail = cb.equal(rootLanguage.get(Language_.official), true);
        query.select(rootLanguage.get(Language_.name))
                .distinct(true)
                .where(cb.and(joinOn, cb.and(cityIn, languageOfficail)));
        return em.createQuery(query).getResultList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String getJpqlEquivalentString() {
        return "SELECT DISTINCT l.name"
                + " FROM City c"
                + " INNER JOIN Language l ON c.country.code=l.country.code"
                + " WHERE c.name IN ('Kyiv', 'London', 'Washington') AND l.official=true";
    }
}
