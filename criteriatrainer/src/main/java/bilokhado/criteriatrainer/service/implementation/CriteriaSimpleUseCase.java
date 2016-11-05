package bilokhado.criteriatrainer.service.implementation;

import bilokhado.criteriatrainer.model.entity.Country;
import bilokhado.criteriatrainer.service.CriteriaCaseService;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by dimon on 05.11.16.
 */
public class CriteriaSimpleUseCase implements CriteriaCaseService {
    @Override
    public List<Country> getResultWithCriteria(EntityManager em) {
        System.out.println("Simple use case");
        return em.createQuery("SELECT c FROM Country c WHERE c.code='UKR'", Country.class).getResultList();
    }
}
