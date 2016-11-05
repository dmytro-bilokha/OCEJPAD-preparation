package bilokhado.criteriatrainer.service.provider;

import bilokhado.criteriatrainer.service.CriteriaCaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Created by dimon on 05.11.16.
 */
public class CriteriaCaseServiceProvider {

    private static CriteriaCaseServiceProvider provider;
    private static ServiceLoader<CriteriaCaseService> loader;

    public static CriteriaCaseServiceProvider getInstanse() {
        if (provider == null)
            provider = new CriteriaCaseServiceProvider();
        return provider;
    }

    private CriteriaCaseServiceProvider() {
        loader = ServiceLoader.load(CriteriaCaseService.class);
    }

    public List<CriteriaCaseService> getImplementations() {
        final List<CriteriaCaseService> serviceList = new ArrayList<>();
        loader.forEach(s -> {
            if (s != null)
                serviceList.add(s);
        });
        return serviceList;
    }
}
