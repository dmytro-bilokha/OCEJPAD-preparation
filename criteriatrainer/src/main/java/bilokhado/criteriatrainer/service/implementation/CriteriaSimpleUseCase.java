package bilokhado.criteriatrainer.service.implementation;

import bilokhado.criteriatrainer.service.CriteriaCaseService;

import java.util.List;

/**
 * Created by dimon on 05.11.16.
 */
public class CriteriaSimpleUseCase implements CriteriaCaseService {
    @Override
    public List<Object> getResultWithCriteria() {
        System.out.println("Simple use case");
        return null;
    }
}
