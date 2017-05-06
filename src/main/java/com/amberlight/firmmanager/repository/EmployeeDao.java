package com.amberlight.firmmanager.repository;

import com.amberlight.firmmanager.model.*;
import com.amberlight.firmmanager.util.TimeAnalyzer;
import org.springframework.dao.DataAccessException;
import java.util.Collection;
import java.util.List;

/**
 * Repository interface for objects of an {@link Employee} class.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
public interface EmployeeDao {

    /**
     * Retrieve a collection of <code>Employee</code>s without fetching user
     * but with <code>Employee</code>'s projects
     * by last name and working position from the data store.
     * @param lastName value to search for
     * @param workingPosition value to search for
     * @return a collection of matching <code>Employee</code>s (or an empty collection if none found)
     * @throws DataAccessException
     */
    Collection<Employee> findEmployeeByLastNameAndWorkingPosition(String lastName, String workingPosition) throws DataAccessException;

    /**
     * Retrieve a list of all working positions from the data base.
     * @return a list of <code>WorkingPosition</code>s
     * @throws DataAccessException
     */
    List<WorkingPosition> findWorkingPositions() throws DataAccessException;

    /**
     * Retrieve an <code>Employee</code> with fetching
     * <code>Employee</code>'s user and without fetching projects by <code>Employee</code>'s
     * id from the data store.
     * @param id the <code>Employee</code>'s id to search for
     * @return the <code>Employee</code> if found or null if found
     * @throws DataAccessException
     */
    Employee findEmployeeById(int id) throws DataAccessException;

    /**
     * Retrieve an <code>Employee</code> with fetching
     * <code>Employee</code>'s user by <code>Employee</code>'s
     * id from the data store.
     * @param id the <code>Employee</code>'s id to search for
     * @return the <code>Employee</code> if found or null if not
     * @throws DataAccessException
     */
    Employee findEmployeeByIdFetchUser(int id) throws DataAccessException;

    /**
     * Retrieve an <code>Employee</code> with fetching
     * <code>Employee</code>'s projects and user by <code>Employee</code>'s id
     * from the data store.
     * @param id the <code>Employee</code>'s id to search for
     * @return the <code>Employee</code> if found or null if not
     * @throws DataAccessException
     */
    Employee findEmployeeByIdFetchProjectsAndUser(int id) throws DataAccessException;

    /**
     * Save <code>Employee</code> to the data store, either inserting or updating it.
     * @param employee the <code>Employee</code> to save
     * @throws DataAccessException
     */
    void saveEmployee(Employee employee) throws DataAccessException;

    /**
     * Delete <code>Employee</code> from the data store with all
     * relations with the {@link Project} entity.
     * At first delete all <code>Employee</code>'s relations with <code>Project</code>s
     * by <code>Employee</code>'s id, then delete <code>Employee</code> by his id.
     * @param employeeId the id to search for
     * @throws DataAccessException
     */
    void removeEmployee(int employeeId) throws DataAccessException;

    /**
     * Retrieve list of all genders from the data store.
     * @return a list of <code>Gender</code>s
     * @throws DataAccessException
     */
    List<Gender> findGenders() throws DataAccessException;

    /**
     * Retrieve {@link WorkingPosition} from the data store by its id.
     * @param workingPositionId the id to search for
     * @return the <code>WorkingPosition</code> if found
     * @throws DataAccessException
     */
    WorkingPosition findWorkingPositionById(int workingPositionId) throws DataAccessException;

    /**
     * Retrieve {@link Gender} from the data store by its id.
     * @param genderId the id to search for
     * @return the <code>Gender</code> if found
     * @throws DataAccessException
     */
    Gender findGenderById(int genderId) throws DataAccessException;

    /**
     * Delete a relationship between certain {@link Employee} and {@link Project} by their id's from the data store.
     * @param projectId the id to search for
     * @param employeeId the id to search for
     * @throws DataAccessException
     */
    void detachProjectFromEmployee(int projectId, int employeeId) throws DataAccessException;

    /**
     * Insert a relationship between a certain {@link Employee} and a {@link Project} by their id's to the data store.
     * @param projectId the id to search for
     * @param employeeId the id to search for
     * @throws DataAccessException
     */
    void attachProjectToEmployee(int projectId, int employeeId) throws DataAccessException;

    /**
     * Retrieve a List of <code>Employee</code>s
     * that haven't certain project as part of related projects with these <code>Employee</code>s.
     * Also fetch <code>Employee</code>'s projects.
     * @param projectId the id to search for
     * @return a collection of matching <code>Employee</code>s (or an empty collection if none found)
     * @throws DataAccessException
     */
    List<Employee> findEmployeesUnrelatedWithProject(int projectId) throws DataAccessException;

    /**
     * Retrieve all <code>Employee</code>s with fetching <code>Employee</code>'s projects.
     * @return a list of matching <code>Employee</code>s (or an empty collection if none found)
     * @throws DataAccessException
     */
    List<Employee> findAllEmployees()  throws DataAccessException;

    /**
     * Update an experience field of <code>Employee</code> in the data store.
     * @param experience the value of the experience
     * @param employeeId the id to search for
     * @throws DataAccessException
     */
    void updateExperience(long experience, int employeeId) throws DataAccessException;

    /**
     * Update an age field of <code>Employee</code> in the data store.
     * @param age the value of the age
     * @param employeeId the id to search for
     * @throws DataAccessException
     */
    void updateAge(long age, int employeeId) throws DataAccessException;

    /**
     * Update a file name of <code>Employee</code>'s photo in the data store.
     * @param filename the file name of <code>Employee</code>'s photo
     * @param employeeId the id to search for
     * @throws DataAccessException
     */
    void updateEmployeePhoto(String filename, int employeeId) throws DataAccessException;


    /**
     * Update age and experience of all <code>Employee</code>s.
     * The algorithm is:
     * For each <code>Employee</code> calculate years differences between current time,
     * birth and hire time, then set respective fields with values of these time differences.
     * @param timeAnalyzer accessory object for helping with time difference calculations
     * @throws DataAccessException
     */
    void updateAgeAndExpOfEmployees(TimeAnalyzer timeAnalyzer) throws DataAccessException;
}
