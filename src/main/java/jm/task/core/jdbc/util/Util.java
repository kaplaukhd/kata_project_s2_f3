package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String HOST = "jdbc:mysql://localhost:3306/kata_f2_s1";

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration conf = new Configuration();
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, DBDRIVER);
            properties.put(Environment.URL, HOST);
            properties.put(Environment.USER, USERNAME);
            properties.put(Environment.PASS, PASSWORD);
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(Environment.HBM2DDL_AUTO, "update");
            conf.setProperties(properties);
            conf.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(conf.getProperties()).build();

            sessionFactory = conf.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection connection() {
        Connection connection = null;
        try {
            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Session getSession() {
        return sessionFactory.getCurrentSession();
    }

}
