package bilokhado;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by dimon on 22.10.16.
 */
@Stateless
public class EmployeeService {

    @PersistenceContext(unitName="hellojpa-ee")
    private EntityManager em;

    public List<Employee> findAllEmployees() {
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
        return query.getResultList();
    }
}
