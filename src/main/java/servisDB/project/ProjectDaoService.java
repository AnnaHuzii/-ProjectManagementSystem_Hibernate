package servisDB.project;

import connection.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import servisDB.company.Company;

import java.util.List;

public class ProjectDaoService implements ProjectService {
    private static final ProjectDaoService INSTANCE;

    static {
        INSTANCE = new ProjectDaoService();
    }

    public static ProjectDaoService getInstance() {
        return INSTANCE;
    }


    public Project getProjectById(long id) {
        Session session = openSession();
        return session.get(Project.class, id);
    }

    @Override
    public Project getProjectByName(String name) {
        Session session = openSession();

        NativeQuery<Project> query = session.createNativeQuery(
                "SELECT * FROM project WHERE name = :paramName",
                Project.class
        );
        query.setParameter("paramName", name);
        List<Project> projectList = query.list();
        System.out.println("projectList = " + projectList);
        Project project = new Project();
        if (projectList.size()<=1) {
            for (Project p: projectList) {
                project.setId(p.getId());
                project.setCost(p.getCost());
            }
        }
        session.close();
        return project;
    }


    public List<Project> getProjectList() {
        try (Session session = openSession()) {
            return session.createQuery("FROM Project ", Project.class).list();
        }
    }

    @Override
    public float getBudgetByProjectName(String name) {
        return 0;
    }

    @Override
    public List<String> listOfProjectDevelopers(String name) {
        return null;
    }

    @Override
    public List<Project> getProjectsListInSpecialFormat() {
        return null;
    }


    public long createProject(String name, Project project) {
        Session session = openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(project);
        transaction.commit();
        session.close();
        return 0;
    }

    private Session openSession() {
        return HibernateUtil.getInstance().getSessionFactory().openSession();
    }

}
