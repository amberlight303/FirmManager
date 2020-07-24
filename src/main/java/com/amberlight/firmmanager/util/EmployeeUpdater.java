package com.amberlight.firmmanager.util;

import com.amberlight.firmmanager.model.Employee;

/**
 * An accessory class for {@link Employee}.
 * Serves for calculating values of <code>Employee</code>'s fields.
 */
public class EmployeeUpdater {

    /**
     * Return <code>Employee</code> after calculation and setting
     * age and experience fields of passed <code>Employee</code> as a parameter.
     * The algorithm is:
     * Calculate years differences between current time,
     * <code>Employee</code>'s birth and hire time, then set respective
     * <code>Employee</code>'s fields with values of these time differences.
     * @param employee the <code>Employee</code> to calculate and set fields
     * @param timeAnalyzer accessory object for helping with time difference calculations
     * @return processed <code>Employee</code>
     */
    public Employee calcAgeAndExpAndSetEmployee(Employee employee, TimeAnalyzer timeAnalyzer){
        long experienceDifference = timeAnalyzer.analyzeYears(employee.getHireDate());
        employee.setExperience((int) experienceDifference);
        long ageDifference = timeAnalyzer.analyzeYears(employee.getBirthDate());
        employee.setAge((int) ageDifference);
        return employee;
    }

    @Override
    public String toString() {
        return "EmployeeUpdater{}";
    }
}
