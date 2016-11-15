package bilokhado.criteriatrainer.service.implementation;

import bilokhado.criteriatrainer.model.entity.Country;
import bilokhado.criteriatrainer.model.entity.Country_;
import bilokhado.criteriatrainer.model.entity.Language;
import bilokhado.criteriatrainer.service.CriteriaCaseService;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Case to test filtering by map's key value
 */
public class FilterByMapKeyCase implements CriteriaCaseService {

    @Override
    public List<Country> getResultWithCriteria(EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Country> query = cb.createQuery(Country.class);
        Root<Country> rootCountry = query.from(Country.class);
        MapJoin<Country, String, Language> joinLanguageMap = rootCountry.join(Country_.languageMap, JoinType.INNER);
        query
                .select(rootCountry)
                .where(cb.equal(joinLanguageMap.key(), "English"));
        return em.createQuery(query).getResultList();
    }

    @Override
    public String getJpqlEquivalentString() {
        return "SELECT c FROM Country c WHERE KEY(c.languageMap)='English'";
    }

}
