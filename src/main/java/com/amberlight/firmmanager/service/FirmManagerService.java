package com.amberlight.firmmanager.service;

import com.amberlight.firmmanager.model.*;
import com.amberlight.firmmanager.util.TimeAnalyzer;
import org.springframework.dao.DataAccessException;
import java.util.Collection;
import java.util.List;

/**
 * Used as a facade so all controllers have a single point of
 * entry in the most functions of persistence level.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
public interface FirmManagerService {

    Collection<Employee> findEmployeeByLastNameAndWorkingPosition(String lastName, String workingPosition) throws DataAccessException;

    List<WorkingPosition> findWorkingPositions() throws DataAccessException;

    Employee findEmployeeById(int id) throws DataAccessException;

    void saveEmployee(Employee employee) throws DataAccessException;

    Collection<Project> findProjectByNameAndProjectObjectiveAndProjectStatus(String name, String projectObjective, String projectStatus) throws DataAccessException;

    List<ProjectObjective> findProjectObjectives() throws DataAccessException;

    List<ProjectStatus> findProjectStatuses() throws DataAccessException;

    Project findProjectById(int id) throws DataAccessException;

    void saveProject(Project project) throws DataAccessException;

    ProjectObjective findProjectObjectiveById(int id) throws DataAccessException;

    ProjectStatus findProjectStatusById(int id) throws DataAccessException;

    void removeProject(int id) throws DataAccessException;

    void removeEmployee(int employeeId) throws DataAccessException;

    void detachProjectFromEmployee(int projectId, int employeeId) throws DataAccessException;

    void detachEmployeeFromProject(int projectId, int employeeId) throws DataAccessException;

    void attachEmployeeToProject(int projectId, int employeeId) throws DataAccessException;

    void attachProjectToEmployee(int projectId, int employeeId) throws DataAccessException;

    List<Employee> findEmployeesUnrelatedWithProject(int projectId) throws DataAccessException;

    List<Gender> findGenders() throws DataAccessException;

    WorkingPosition findWorkingPositionById(int workingPositionId) throws DataAccessException;

    Gender findGenderById(int genderId) throws DataAccessException;

    List<Project> findProjectsUnrelatedWithEmployee(int employeeId) throws DataAccessException;

    void saveComment(Comment comment) throws DataAccessException;

    void savePost(Post post) throws DataAccessException;

    Collection<Post> findPosts() throws DataAccessException;

    Post findPostById(int id) throws DataAccessException;

    List<Employee> findAllEmployees() throws DataAccessException;

    Long countPosts() throws DataAccessException;

    List<Post> findPosts(int page, int numberOfPosts) throws DataAccessException;

    void updateExperience(long experience, int employeeId) throws DataAccessException;

    void updateAge(long age, int employeeId) throws DataAccessException;

    TimeCounter getTimeCounterById(int counterId) throws DataAccessException;

    void updateDaysFromStart(long daysFromStart, int counterId) throws DataAccessException;

    void deleteUserByUserId(int userId) throws DataAccessException;


    void deleteCommentsByUserId(int userId) throws DataAccessException;

    void updateEmployeePhoto(String filename, int employeeId) throws DataAccessException;

    void updatePostImage(String filename, int postId) throws DataAccessException;

    void deletePost(int postId) throws DataAccessException;

    List<Project> findAllProjects() throws DataAccessException;

    Collection<User> findUsersByLastNameAndUserName(String lastName, String userName) throws DataAccessException;

    Employee findEmployeeByIdFetchProjectsAndUser(int id) throws DataAccessException;

    Project findProjectByIdFetchEmployees(int id) throws DataAccessException;

    Post findPostByIdFetchComments(int id) throws DataAccessException;

    void updateAgeAndExpOfEmployees(TimeAnalyzer timeAnalyzer) throws DataAccessException;

    void updateStatusesAndDaysLeftOfProjects(TimeAnalyzer timeAnalyzer) throws DataAccessException;

    Post findPostByIdFetchLikes(int id) throws DataAccessException;

    boolean didUserLikePost(int postId, int userId) throws DataAccessException;

    void saveLike(Like like) throws DataAccessException;

    void removeLikeFromPostByUserId(int userId, int postId) throws DataAccessException;

    void updateAmountOfLikes(int postId, int amountOfLikes)  throws DataAccessException;

    Employee findEmployeeByIdFetchUser(int id) throws DataAccessException;

}
