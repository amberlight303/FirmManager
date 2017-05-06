package com.amberlight.firmmanager.validator;

import com.amberlight.firmmanager.model.Employee;
import com.amberlight.firmmanager.model.Project;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link Project} forms,
 * implements {@link Validator} interface.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@Component
public class ProjectValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Employee.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Project project = (Project) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "endDate", "Required");
    }
}
