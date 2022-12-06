package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Session session = Util.getSession();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS User" +
                "(id int not null auto_increment," +
                " name VARCHAR(50), " +
                "lastname VARCHAR(50), " +
                "age int, " +
                "PRIMARY KEY (id));").addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        session.beginTransaction();
        session.createSQLQuery("Drop table if exists kata_f2_s1.user;").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        session.beginTransaction();
        session.createSQLQuery("DELETE FROM kata_f2_s1.user WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        session.beginTransaction();
        userList = session.createQuery("from User").getResultList();
        session.getTransaction().commit();
        session.close();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE TABLE User;")
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
