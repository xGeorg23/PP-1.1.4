package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    public static final String url = "jdbc:mysql://localhost:3306/mytest";
    public static final String username = "root";
    public static final String password = "root1";
    
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("У нас проблемы");
            e.printStackTrace();
        }
        return connection;
    }

    // Hibernate
    private static SessionFactory sessionFactory = null;

    static {       // статический блок, что бы инициализировать настройки хибернейт один раз
        try {
            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.url", url);
            properties.setProperty("hibernate.connection.username", username);
            properties.setProperty("hibernate.connection.password", password);
            properties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");

            sessionFactory = new Configuration().addAnnotatedClass(User.class).addProperties(properties).buildSessionFactory();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
    }
    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }
}
