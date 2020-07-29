package com.amberlight.firmmanager.validator;

import com.amberlight.firmmanager.model.User;
import com.amberlight.firmmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link com.amberlight.firmmanager.model.User} class, implements {@link Validator} interface.
 */
@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "Required");
        char[] chars = user.getFirstName().toCharArray();
        for(char c: chars) {
            if (!Character.isLetter(c)) {
                errors.rejectValue("firstName", "Characters.onlyLetters");
                break;
            }
        }
        chars = user.getLastName().toCharArray();
        for(char c: chars) {
            if (!Character.isLetter(c)) {
                errors.rejectValue("lastName", "Characters.onlyLetters");
                break;
            }
        }
        if (user.getUsername().length() < 4 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (user.getPassword().length() < 4 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }
    }

    @Override
    public String toString() {
        return "UserValidator{" +
                "userService=" + userService +
                '}';
    }
}
