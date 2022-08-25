package servisDB.project;

import connection.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import servisDB.developer.Developer;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

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
    public String getProjectByName(String name) {
        Session session = openSession();
        StringJoiner result = new StringJoiner("<br>");
        NativeQuery<String> queryCustomers = session.createNativeQuery(
                "SELECT customers.name FROM project " +
                        "JOIN customers ON project.customer_id = customers.id " +
                        "WHERE name = :paramName",
                String.class
        );
        queryCustomers.setParameter("paramName", name);
        List<String> projectListCustomers = queryCustomers.list();
        String customersName = projectListCustomers.get(0);
        result.add("Ordered by the customer: " + customersName);

        NativeQuery<String> queryCompanies = session.createNativeQuery(
                "SELECT companies.name FROM project " +
                        "JOIN companies ON project.company_id = companies.id " +
                        "WHERE name = :paramName",
                String.class
        );
        queryCompanies.setParameter("paramName", name);
        List<String> projectListCompanies = queryCompanies.list();
        String companiesName = projectListCompanies.get(0);
        result.add("Companies: " + companiesName);
        NativeQuery<Project> queryDanaProject = session.createNativeQuery(
                "SELECT cost, start_date, description FROM project " +
                        "WHERE  name = :paramName",
                Project.class
        );
        queryCompanies.setParameter("paramName", name);
        List<Project> ListDanaProject = queryDanaProject.list();
        for (Project project : ListDanaProject) {
            result.add("Project description: " + project.getDescription());
            result.add("Has a budget - " + project.getCost());
            result.add("Started by: " + project.getStartDate());
        }
        return result.toString();
    }


    public List<Project> getProjectList() {
        try (Session session = openSession()) {
            return session.createQuery("FROM Project ", Project.class).list();
        }
    }

    @Override
    public float getBudgetByProjectName(String name) {
        Session session = openSession();

        NativeQuery<Float> query = session.createNativeQuery(
                "SELECT SUM(salary) FROM project " +
                        "JOIN projects_developers ON project.id = projects_developers.project_id " +
                        "JOIN developers ON projects_developers.developer_id = developers.id " +
                        "WHERE name = :paramName", Float.class
        );
        query.setParameter("paramName", name);
        List<Float> projectList = query.list();

        return projectList.get(0);
    }

    @Override
    public String listOfProjectDevelopers(String name) {
        Session session = openSession();

        NativeQuery<Developer> query = session.createNativeQuery("SELECT full_name, birth_date FROM project " +
                        "JOIN projects_developers ON project.id = projects_developers.project_id " +
                        "JOIN developers ON projects_developers.developer_id = developers.id " +
                        "WHERE name = :paramName", Developer.class
        );
        query.setParameter("paramName", name);
        List<Developer> projectList = query.list();

        StringJoiner result = new StringJoiner("<br>");
       for (Developer developer : projectList){
                result.add(developer.getFullName() + ", Birth date: " + developer.getBirthDate());
        }
        return result.toString();
    }

    @Override
    public String getProjectsListInSpecialFormat() {
        List<Project> projectList = getProjectList();
        Session session = openSession();
        StringJoiner result = new StringJoiner("<br>");
        StringJoiner list = new StringJoiner(", ");
        for (Project project : projectList) {
            String projectName = project.getName();
            NativeQuery<Integer> query = session.createNativeQuery(
                    "SELECT COUNT(developer_id) FROM project " +
                            "JOIN projects_developers ON project.id = projects_developers.project_id " +
                            "WHERE name = :paramName", Integer.class);
            query.setParameter("paramName", projectName);
            List<Integer> numberDevelopers = query.list();
            Date projectDate = project.getStartDate();
            list.add("Project start date: " + projectDate);
            list.add("Project name: " + projectName);
            list.add("quantity developers = " + numberDevelopers.size());
        }

        return result.add(list.toString()).toString();
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
