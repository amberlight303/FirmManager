package com.amberlight.firmmanager.repository;

import com.amberlight.firmmanager.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Spring Data JPA repository interface for objects of {@link User} class.
 * It's a common interface also extending custom spring data jpa interface {@link UserDaoCustom}.
 */
public interface UserDao extends JpaRepository<User, Integer>, UserDaoCustom {

    /**
     * Retrieve <code>User</code> from the data store by his username.
     * @param username the value to search for
     * @return the <code>User</code> if found
     * @throws DataAccessException
     */
    User findByUsername(String username) throws DataAccessException;

    /**
     * Retrieve a list of all <code>User</code>s from the data store.
     * @return a list of all <code>User</code>s (or empty list if none found)
     * @throws DataAccessException
     */
    List<User> findAll() throws DataAccessException;
}
