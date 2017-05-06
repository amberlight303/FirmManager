package com.amberlight.firmmanager.repository.jpa;

import com.amberlight.firmmanager.repository.UserDaoCustom;
import com.amberlight.firmmanager.model.User;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * JPA implementation of {@link UserDaoCustom} interface.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@Repository("userDao")
public class UserDaoImpl implements UserDaoCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void attachUserToEmployee(int userId, int employeeId) {
        Query query = this.em.createNativeQuery("UPDATE users SET employee_id = ? WHERE id = ?");

        query.setParameter(1, employeeId);
        query.setParameter(2, userId);

        query.executeUpdate();
    }

    @Override
    public void deleteUserByUserId(int userId) {
        Query query1 =  this.em.createNativeQuery("DELETE FROM user_roles WHERE user_id = ?");
        query1.setParameter(1, userId);
        query1.executeUpdate();
        Query query2 = this.em.createNativeQuery("DELETE FROM comments WHERE user_id = ?");
        query2.setParameter(1, userId);
        query2.executeUpdate();
        Query query3 =  this.em.createNativeQuery("DELETE FROM users WHERE id = ?");
        query3.setParameter(1, userId);
        query3.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> findUsersByLastNameAndUserName(String lastName, String userName) {
        Query query = this.em.createQuery(
                "SELECT user FROM User user LEFT JOIN FETCH user.employee " +
                        "WHERE user.lastName LIKE :lastName " +
                        "AND user.username LIKE :userName ");
        query.setParameter("lastName", lastName + "%");
        query.setParameter("userName", userName + "%");
        return query.getResultList();
    }

    @Override
    public User findUserById(int id) {
        return this.em.find(User.class, id);
    }

    @Override
    public User findUserByIdFetchEmployee(int id) {
        Query query = this.em.createQuery("SELECT user FROM User user LEFT JOIN FETCH user.employee WHERE user.id = :id");
        query.setParameter("id", id);
        return (User) query.getSingleResult();
    }

    @Override
    public User findUserByUserNameFetchEmployee(String userName) {
        Query query = this.em.createQuery("SELECT DISTINCT user FROM User user " +
                "LEFT JOIN FETCH user.employee " +
                "WHERE user.username = :userName");
        query.setParameter("userName", userName);
        return (User) query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        Query query = this.em.createQuery("SELECT user FROM User user LEFT JOIN FETCH user.employee");
        return query.getResultList();
    }
}
