package servisDB.customer;

import connection.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;


public class CustomerDaoService implements CustomerService {

    private static final CustomerDaoService INSTANCE;

    static {
        INSTANCE = new CustomerDaoService();
    }

    public static CustomerDaoService getInstance() {
        return INSTANCE;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(customer);
        transaction.commit();
        session.close();
        return customer;
    }

    @Override
    public Customer getById(long id) {
        try(Session session = openSession()) {
            return session.get(Customer.class, id);
        }
    }

    @Override
    public List<Customer> getCustomerAll() {
        try(Session session = openSession()) {
            return session.createQuery("FROM Customer", Customer.class).list();
        }
    }

    @Override
    public String update(Customer customer) {
        try {
            Session session = openSession();
            Transaction transaction = session.beginTransaction();
            session.merge(customer);
            transaction.commit();
            session.close();
            return "true";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public String deleteById(long id) {
        try {
            Session session = openSession();
            Transaction transaction = session.beginTransaction();
            session.remove(getById(id));
            transaction.commit();
            session.close();
            return "true";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    private Session openSession() {
        return HibernateUtil.getInstance().getSessionFactory().openSession();
    }
}
