package bilokhado.criteriatrainer.service.provider;

import bilokhado.criteriatrainer.service.CriteriaCaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Class represents service provider which is used to obtain on CriteriaCaseServices in the application
 */
public class CriteriaCaseServiceProvider {

    private static CriteriaCaseServiceProvider provider;
    private static ServiceLoader<CriteriaCaseService> loader;

    /**
     * Static method to get instance of the provider
     * @return provider's object instance
     */
    public static CriteriaCaseServiceProvider getInstance() {
        if (provider == null)
            provider = new CriteriaCaseServiceProvider();
        return provider;
    }

    private CriteriaCaseServiceProvider() {
        loader = ServiceLoader.load(CriteriaCaseService.class);
    }

    /**
     * Method to get all services implementing CriteriaCaseService interface
     * @return list of service objects
     */
    public List<CriteriaCaseService> getImplementations() {
        final List<CriteriaCaseService> serviceList = new ArrayList<>();
        loader.forEach(s -> {
            if (s != null)
                serviceList.add(s);
        });
        return serviceList;
    }

}
