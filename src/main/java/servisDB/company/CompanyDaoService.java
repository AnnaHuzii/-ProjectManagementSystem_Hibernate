package servisDB.company;

import connection.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CompanyDaoService implements CompanyService {

    private static final CompanyDaoService INSTANCE;

    static {
        INSTANCE = new CompanyDaoService();
    }

    public static CompanyDaoService getInstance() {
        return INSTANCE;
    }

    @Override
    public Company createCompany(Company company) {
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(company);
        transaction.commit();
        session.close();
        return company;
    }

    @Override
    public Company getCompanyById(long id) {
        Session session = openSession();
        return session.get(Company.class, id);
    }

    @Override
    public List<Company> getCompanyAll() {
        try (Session session = openSession()) {
            return session.createQuery("FROM Company", Company.class).list();
        }
    }

    @Override
    public boolean updateCompany(long id, Company company) {
        try {
            Session session = openSession();
            Transaction transaction = session.beginTransaction();
            String companyName = company.getCompanyName();
            String description = company.getCompanyDescription();
            Company existing = session.get(Company.class, id);
            existing.setCompanyName(companyName);
            existing.setCompanyDescription(description);
            session.merge(existing);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Override
    public boolean deleteByIdCompany(long id) {
        try {
            Session session = openSession();
            Transaction transaction = session.beginTransaction();
            session.remove(getCompanyById(id));
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private Session openSession() {
        return HibernateUtil.getInstance().getSessionFactory().openSession();
    }
}
