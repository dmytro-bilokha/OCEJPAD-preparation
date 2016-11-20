package bilokhado.exceptionexplorer.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * The service to get ids pool for persisting test item to the database.
 * Also provides ability to count beans by type.
 */
@Startup
@Singleton
public class BookIdPoolService {

    private static final Logger LOG = LoggerFactory.getLogger(BookIdPoolService.class);
    private static final String FAIL_MESSAGE = "Unable to get maximum used Id value from the database";

    @Resource(mappedName = "jdbc/h2mem")
    DataSource dataSource;

    @PersistenceContext(unitName = "h2mem")
    EntityManager em;

    private int minimalFreeId;
    private Map<String, Integer> beanCountMap = new HashMap<>();

    @PostConstruct
    private void init() {
        Integer initialId;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM item");
        ){
            resultSet.next();
            initialId = resultSet.getInt(1);
        } catch (SQLException | NullPointerException ex) {
            LOG.error(FAIL_MESSAGE, ex);
            throw new EJBException(FAIL_MESSAGE, ex);
        }
        minimalFreeId = initialId + 1;
    }

    /**
     * Books pool of unique id numbers of given size
     * @param poolSize size of the requested pool
     * @return first free id (beginning of the pool)
     */
    public int bookIdPool(int poolSize) {
        if (poolSize < 1)
            throw new IllegalArgumentException("Pool size must be at least 1, but got requested " + poolSize);
        int oldFreeId = minimalFreeId;
        minimalFreeId += poolSize;
        return oldFreeId;
    }

    /**
     * Increments internal counter of type provided and returns new counter value
     * @param type type of the bean to count
     * @return new counter value
     */
    public int countEjbByType(String type) {
        Integer currentCount = beanCountMap.getOrDefault(type, Integer.valueOf(0));
        beanCountMap.put(type, ++currentCount);
        return currentCount;
    }

    /**
     * Returns value of the counter by type
     * @param type type of counter to return
     * @return counter value
     */
    public int getEjbCounter(String type) {
        return beanCountMap.getOrDefault(type, Integer.valueOf(0));
    }
}
