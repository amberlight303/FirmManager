package com.amberlight.firmmanager.service;

import com.amberlight.firmmanager.model.User;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Service class for {@link com.amberlight.firmmanager.model.User}.
 */

public interface UserService {

    void save(User user) throws DataAccessException;

    User findByUsername(String username) throws DataAccessException;

    List<User> findAll() throws DataAccessException;

    void attachUserToEmployee(int userId, int employeeId) throws DataAccessException;

    User findUserByIdFetchEmployee(int id) throws DataAccessException;

    User findUserByUserNameFetchEmployee(String userName) throws DataAccessException;

    User findUserById(int id) throws DataAccessException;
}
