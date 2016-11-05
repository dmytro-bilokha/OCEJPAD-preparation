package bilokhado.criteriatrainer;

import bilokhado.criteriatrainer.service.CriteriaCaseService;
import bilokhado.criteriatrainer.service.provider.CriteriaCaseServiceProvider;

import java.util.List;

/**
 * Created by dimon on 05.11.16.
 */
public class CriteriaTrainer {

    public static void main(String[] commandLine) {
        List<CriteriaCaseService> serviceList = CriteriaCaseServiceProvider.getInstanse().getImplementations();
        for (CriteriaCaseService service : serviceList) {
            service.getResultWithCriteria();
        }
    }
}
