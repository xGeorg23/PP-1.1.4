package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try(Session session = Util.getSession()) {
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT(3) NOT NULL)";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();

        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = Util.getSession()) {
            String sql = "DROP TABLE IF EXISTS users";
            session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных.");
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getSession()) {
            User user = session.get(User.class, id);
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        try(Session session = Util.getSession()) {
            session.beginTransaction();
            userList = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSession()) {
            session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException exception) {
            exception.printStackTrace();
        }
    }
}
