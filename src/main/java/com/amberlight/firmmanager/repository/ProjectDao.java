package com.amberlight.firmmanager.repository;

import com.amberlight.firmmanager.model.Project;
import com.amberlight.firmmanager.model.ProjectObjective;
import com.amberlight.firmmanager.model.ProjectStatus;
import org.springframework.dao.DataAccessException;
import java.util.Collection;
import java.util.List;

/**
 * A repository interface for objects of {@link Project} class.
 */
public interface ProjectDao {

    /**
     * Retrieve a collection of <code>Project</code>s with fetching employees
     * by <code>Project</code>'s name, objective and status from the data store.
     * @param name the value of <code>Project</code>'s name to search for
     * @param projectObjective the value of <code>Project</code>'s objective to search for
     * @param projectStatus the value of <code>Project</code>'s status to search for
     * @return a collection of matching <code>Employee</code>'s (or an empty collection if none found)
     * @throws DataAccessException
     */
    Collection<Project> findProjectByNameAndProjectObjectiveAndProjectStatus(String name, String projectObjective, String projectStatus) throws DataAccessException;

    /**
     * Retrieve all <code>ProjectObjective</code>s from the data store.
     * @return list of all <code>ProjectObjective</code>s (or empty list if none found)
     * @throws DataAccessException
     */
    List<ProjectObjective> findProjectObjectives() throws DataAccessException;

    /**
     * Retrieve all <code>ProjectStatus</code>es.
     * @return list of all <code>ProjectStatus</code>es(or empty list if none found)
     * @throws DataAccessException
     */
    List<ProjectStatus> findProjectStatuses() throws DataAccessException;

    /**
     * Retrieve a <code>ProjectObjective</code> by its id from the data store.
     * @param id the id to search for
     * @return the <code>ProjectObjective</code> if found
     * @throws DataAccessException
     */
    ProjectObjective findProjectObjectiveById(int id) throws DataAccessException;

    /**
     * Retrieve a <code>ProjectStatus</code> by its id from the data store.
     * @param id the id to search for
     * @return the <code>ProjectStatus</code> if found
     * @throws DataAccessException
     */
    ProjectStatus findProjectStatusById(int id) throws DataAccessException;

    /**
     * Retrieve a <code>Project</code> by its id  from the data store without fetching employees.
     * @param id the id to search for <code>Project</code>
     * @return the <code>Project</code> if found or null if not
     * @throws DataAccessException
     */
    Project findProjectById(int id) throws DataAccessException;

    /**
     * Retrieve a <code>Project</code> by its id  from the data store with fetching employees.
     * @param id the id to search for <code>Project</code>
     * @return the <code>Project</code> if found or null if not
     * @throws DataAccessException
     */
    Project findProjectByIdFetchEmployees(int id) throws DataAccessException;

    /**
     * Save <code>Project</code>, either updating or inserting it to the data store.
     * @param project the <code>Project</code> to save
     * @throws DataAccessException
     */
    void saveProject(Project project) throws DataAccessException;

    /**
     * Delete a <code>Project</code> from the data store by its id.
     * @param id the value to search for
     * @throws DataAccessException
     */
    void removeProject(int id) throws DataAccessException;

    /**
     * Delete a relation between a certain
     * {@link Project} and an {@link com.amberlight.firmmanager.model.Employee}
     * by their id's from the data store.
     * @param projectId the id to search for
     * @param employeeId the id to search for
     * @throws DataAccessException
     */
    void detachEmployeeFromProject(int projectId, int employeeId) throws DataAccessException;

    /**
     * Insert a relation between certain
     * {@link Project} and {@link com.amberlight.firmmanager.model.Employee}
     * by their id's from the data store.
     * @param projectId the id to search for
     * @param employeeId the id to search for
     * @throws DataAccessException
     */
    void attachEmployeeToProject(int projectId, int employeeId) throws DataAccessException;

    /**
     * Retrieve a list of <code>Project</code>s
     * that haven't certain {@link com.amberlight.firmmanager.model.Employee}
     * as part of related <code>Employee</code>s with these <code>Project</code>s.
     * Also fetch <code>Project</code>'s employees.
     * @param employeeId the id to search for
     * @return a collection of matching <code>Projects</code>s (or an empty collection if none found)
     * @throws DataAccessException
     */
    List<Project> findProjectsUnrelatedWithEmployee(int employeeId) throws DataAccessException;

    /**
     * Retrieve all <code>Project</code>s with fetching <code>Project</code>'s employees.
     * @return a list of matching <code>Project</code>s (or an empty collection if none found)
     * @throws DataAccessException
     */
    List<Project> findAllProjects() throws DataAccessException;

}
