package connection;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import servisDB.company.Company;
import servisDB.customer.Customer;
import servisDB.developer.Developer;
import servisDB.project.Project;
import servisDB.skill.Skills;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class HibernateUtil {
    private static final HibernateUtil INSTANCE;
    @Getter
    private SessionFactory sessionFactory;

    static {
        INSTANCE = new HibernateUtil();
    }

    private HibernateUtil() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream reader = loader.getResourceAsStream("hibernate.properties");

        Properties properties = new Properties();
        try {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String dbUrl = properties.getProperty("hibernate.connection.url");
        String dbUser = properties.getProperty("hibernate.connection.username");
        String dbPass = properties.getProperty("hibernate.connection.password");

        sessionFactory = new Configuration()
                .setProperty("hibernate.connection.url",dbUrl)
                .setProperty("hibernate.connection.username", dbUser)
                .setProperty("hibernate.connection.password", dbPass)
               // .addAnnotatedClass(Developer.class)
                .addAnnotatedClass(Company.class)
               // .addAnnotatedClass(Skills.class)
               // .addAnnotatedClass(Project.class)
               // .addAnnotatedClass(Customer.class)
                .buildSessionFactory();
    }

    public static HibernateUtil getInstance() {
        return INSTANCE;
    }

    public void close() {
        sessionFactory.close();
    }

}
