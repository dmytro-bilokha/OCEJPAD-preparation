package bilokhado.criteriatrainer.service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by dimon on 05.11.16.
 */
public interface CriteriaCaseService {
    public List<? extends Object> getResultWithCriteria(EntityManager em);
}
