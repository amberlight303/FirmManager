package com.amberlight.firmmanager.validator;

import com.amberlight.firmmanager.model.Employee;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * <code>Validator</code> for {@link Employee} forms,
 * implements {@link Validator} interface
 */
@Component
public class EmployeeValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Employee.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Employee employee = (Employee) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telephone", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hireDate", "Required");
        char[] chars = employee.getFirstName().toCharArray();
        for(char c: chars){
            if(!Character.isLetter(c) && c != ' '){
                errors.rejectValue("firstName", "Characters.onlyLetters");
                break;
            }
        }
        chars = employee.getLastName().toCharArray();
        for(char c: chars){
            if(!Character.isLetter(c) && c != ' '){
                errors.rejectValue("lastName", "Characters.onlyLetters");
                break;
            }
        }
        chars = employee.getCity().toCharArray();
        for(char c: chars){
            if(!Character.isLetter(c) && c != ' '){
                errors.rejectValue("city", "Characters.onlyLetters");
                break;
            }
        }
        chars = employee.getCountry().toCharArray();
        for(char c: chars){
            if(!Character.isLetter(c) && c != ' '){
                errors.rejectValue("country", "Characters.onlyLetters");
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "EmployeeValidator{}";
    }
}
