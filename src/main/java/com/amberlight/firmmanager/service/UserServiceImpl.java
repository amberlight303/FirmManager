package com.amberlight.firmmanager.service;

import com.amberlight.firmmanager.repository.RoleDao;
import com.amberlight.firmmanager.repository.UserDao;
import com.amberlight.firmmanager.model.Role;
import com.amberlight.firmmanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An implementation of the {@link UserService} interface.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getOne(1));
        user.setRoles(roles);
        userDao.save(user);
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return this.userDao.findAll();
    }

    @Override
    @Transactional
    public void attachUserToEmployee(int userId, int employeeId) {
        this.userDao.attachUserToEmployee(userId, employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByIdFetchEmployee(int id) {
        return this.userDao.findUserByIdFetchEmployee(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByUserNameFetchEmployee(String userName) {
        return this.userDao.findUserByUserNameFetchEmployee(userName);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(int id) {
        return this.userDao.findUserById(id);
    }
}
