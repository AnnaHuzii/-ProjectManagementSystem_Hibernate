package servisDB.developer;

import connection.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import java.sql.Date;
import java.util.List;

public class DeveloperDaoService implements DeveloperService {
    private static final DeveloperDaoService INSTANCE;

    static {
        INSTANCE = new DeveloperDaoService();
    }

    public static DeveloperDaoService getInstance() {
        return INSTANCE;
    }

    @Override
    public Developer getDeveloperById(long id) {
        Session session = openSession();
        Developer developer = session.get(Developer.class, id);
        session.close();
        return developer;
    }

    @Override
    public List<Developer> getListDeveloper() {
        Session session = openSession();
        return session.createQuery("from Developer", Developer.class).list();
    }

    @Override
    public Developer createDeveloper(Developer developer) {
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(developer);
        transaction.commit();
        return developer;
    }

    @Override
    public List<Developer> getDeveloperByNameAndDate(String fullName, Date birthDate) {
        try (Session session = openSession()) {

            Transaction transaction = session.beginTransaction();
            NativeQuery<Developer> developerQuery = session.createNativeQuery(
                    "SELECT sex, email, skype, salary FROM developer" +
                            " WHERE full_name = :paramFullName AND" +
                            " birth_date = :paramBirthDate",
                    Developer.class);
            developerQuery.setParameter("paramFullName", fullName);
            developerQuery.setParameter("paramBirthDate", birthDate);
            transaction.commit();
            return developerQuery.list();
        }
    }

    @Override
    public List<Developer> getListMiddleDevelopers() {
        try (Session session = openSession()) {

            Transaction transaction = session.beginTransaction();
            NativeQuery<Developer> developerQuery = session.createNativeQuery(
        "SELECT full_name, industry " +
                "FROM developer " +
                "JOIN developer_skill ON developer.id = developer_skill.developer_id " +
                "JOIN skill ON developer_skill.skill_id = skill.id " +
                "WHERE skill_level = 'middle'",
                    Developer.class);
            transaction.commit();
        return developerQuery.list();
        }
    }

    @Override
    public boolean updateDeveloper(Developer developer) {
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(developer);
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteDeveloperById(long id) {
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        Developer developerToDelete = getDeveloperById(id);
        if (developerToDelete != null) {
            session.remove(developerToDelete);
            transaction.commit();
            session.close();
            return true;
        } else {
            transaction.commit();
            session.close();
            return false;
        }
    }


    @Override
    public int getDevelopersSkillLevel(String skillLevel) {

        try(Session session = openSession()) {
            NativeQuery<Developer> nativeQuery = session.createNativeQuery(
                    "SELECT T1.*" + '\n' +
                            "FROM developer AS T1" + '\n' +
                            "JOIN developer_skill AS T2 ON T1.id=T2.developer_id" + '\n' +
                            "JOIN skill AS T3 ON T2.skill_id=T3.id" + '\n' +
                            "GROUP BY T3.skill_level , T1.id" + '\n' +
                            "HAVING T3.skill_level = :skillLevel",
                    Developer.class
            );
            nativeQuery.setParameter("skillLevel", skillLevel);
            List<Developer> developers = nativeQuery.list();

            return developers.size();
        }
    }

    private Session openSession() {
        return HibernateUtil.getInstance().getSessionFactory().openSession();
    }

}
