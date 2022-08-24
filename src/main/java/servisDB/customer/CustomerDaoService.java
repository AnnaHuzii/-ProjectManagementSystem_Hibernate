package servisDB.customer;

import connection.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import servisDB.company.Company;
import servisDB.company.CompanyDaoService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class CustomerDaoService implements CustomerService{

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
    public Customer getCustomerById(long id) {
        Session session = openSession();
        return session.get(Customer.class, id);
    }

    @Override
    public List<Customer> getCustomerAll() {
        try (Session session = openSession()) {
            return session.createQuery("FROM Customer", Customer.class).list();
        }
    }

    @Override
    public boolean update(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

    private Session openSession() {
        return HibernateUtil.getInstance().getSessionFactory().openSession();
    }
}
