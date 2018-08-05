package com.amberlight.firmmanager.web;

import com.amberlight.firmmanager.model.User;
import com.amberlight.firmmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;


/**
 * A controller for pages related with the {@link User} entity.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@org.springframework.web.bind.annotation.ControllerAdvice(basePackages = "com.amberlight.firmmanager.web")
public class ControllerAdvice {

    @Autowired
    UserService userService;

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        try{
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String currentUserUsername = auth.getName();
            return this.userService.findUserByUserNameFetchEmployee(currentUserUsername);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "ControllerAdvice{" +
                "userService=" + userService +
                '}';
    }
}
