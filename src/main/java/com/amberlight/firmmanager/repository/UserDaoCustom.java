package com.amberlight.firmmanager.repository;

import com.amberlight.firmmanager.model.Role;
import com.amberlight.firmmanager.model.User;
import org.springframework.dao.DataAccessException;
import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA custom repository interface for objects of {@link Role} class.
 */
public interface UserDaoCustom {

    /**
     * Insert a relation between a {@link User} and an {@link com.amberlight.firmmanager.model.Employee}
     * in the data store.
     * @param userId the id to search for
     * @param employeeId the id to insert
     * @throws DataAccessException
     */
    void attachUserToEmployee(int userId, int employeeId) throws DataAccessException;

    /**
     * Delete completely a <code>User</code> from the data store by his id.
     * At first delete all <code>User</code>'s relations with the {@link Role} entity.
     * At second delete all <code>User</code>'s relations
     * with the {@link com.amberlight.firmmanager.model.Comment} entity.
     * At third delete <code>User</code>.
     * @param userId the id to search for
     * @throws DataAccessException
     */
    void deleteUserByUserId(int userId) throws DataAccessException;

    /**
     * Retrieve a collection of <code>User</code>s from the data store
     * by matching with last name and username.
     * With each retrieving <code>User</code> fetch <code> related <code>employee</code> to him.
     * @param lastName the last name to search for
     * @param userName the username to search for
     * @return a collection of matching <code>User</code>s (or an empty collection if none found)
     * @throws DataAccessException
     */
    Collection<User> findUsersByLastNameAndUserName(String lastName, String userName) throws DataAccessException;

    /**
     * Retrieve <code>User</code> from the data store by its id
     * without fetching related <code>employee</code>.
     * @param id the id to search for
     * @return the <code>User</code> if found
     * @throws DataAccessException
     */
    User findUserById(int id) throws DataAccessException;

    /**
     * Retrieve <code>User</code> from the data store by its id
     * with fetching related <code>employee</code>.
     * @param id the id to search for
     * @return the <code>User</code> if found
     * @throws DataAccessException
     */
    User findUserByIdFetchEmployee(int id) throws DataAccessException;

    /**
     * Retrieve a <code>User</code> from the data store by matching with username.
     * Fetch related <code>employee</code>.
     * @param userName the username to search for
     * @return the <code>User</code> if found
     * @throws DataAccessException
     */
    User findUserByUserNameFetchEmployee(String userName) throws DataAccessException;

    /**
     * Retrieve a collection of all <code>User</code>s from the data store.
     * With each retrieving <code>User</code> fetch <code> related to him <code>employee</code>.
     * @return a collection of all <code>User</code>s (or an empty collection if none found)
     * @throws DataAccessException
     */
    List<User> findAll() throws DataAccessException;
}
