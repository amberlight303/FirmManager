package com.amberlight.firmmanager.service;

import com.amberlight.firmmanager.repository.*;
import com.amberlight.firmmanager.model.*;
import com.amberlight.firmmanager.util.TimeAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * An implementation of the {@link FirmManagerService} interface. Used as a facade so all
 * controllers have a single point of entry in the persistence level.
 * Also it's a placeholder for @Transactional annotations.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@Service
public class FirmManagerServiceImpl implements FirmManagerService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private LikeDao likeDao;

    @Autowired
    private TimeCounterDao timeCounterDao;

    @Override
    public Collection<Employee> findEmployeeByLastNameAndWorkingPosition(String lastName, String workingPosition) {
        return employeeDao.findEmployeeByLastNameAndWorkingPosition(lastName,workingPosition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkingPosition> findWorkingPositions() {
        return employeeDao.findWorkingPositions();
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findEmployeeById(int id) {
        return employeeDao.findEmployeeById(id);
    }

    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        employeeDao.saveEmployee(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Project> findProjectByNameAndProjectObjectiveAndProjectStatus(String name, String projectObjective, String projectStatus) {
        return projectDao.findProjectByNameAndProjectObjectiveAndProjectStatus(name,projectObjective,projectStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectObjective> findProjectObjectives() {
        return projectDao.findProjectObjectives();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectStatus> findProjectStatuses() {
        return projectDao.findProjectStatuses();
    }

    @Override
    @Transactional(readOnly = true)
    public Project findProjectById(int id) {
        return projectDao.findProjectById(id);
    }

    @Override
    @Transactional
    public void saveProject(Project project) {
        projectDao.saveProject(project);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectObjective findProjectObjectiveById(int id) {
        return this.projectDao.findProjectObjectiveById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectStatus findProjectStatusById(int id) {
        return this.projectDao.findProjectStatusById(id);
    }

    @Override
    @Transactional
    public void removeProject(int id) {
        this.projectDao.removeProject(id);
    }

    @Override
    @Transactional
    public void removeEmployee(int employeeId) {
        this.employeeDao.removeEmployee(employeeId);
    }

    @Override
    @Transactional
    public void detachProjectFromEmployee(int projectId, int employeeId) {
        this.projectDao.detachEmployeeFromProject(projectId, employeeId);
    }

    @Override
    @Transactional
    public void detachEmployeeFromProject(int projectId, int employeeId) {
        this.employeeDao.detachProjectFromEmployee(projectId, employeeId);
    }

    @Override
    @Transactional
    public void attachEmployeeToProject(int projectId, int employeeId) {
        this.projectDao.attachEmployeeToProject(projectId, employeeId);
    }

    @Override
    @Transactional
    public void attachProjectToEmployee(int projectId, int employeeId) {
        this.employeeDao.attachProjectToEmployee(projectId, employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findEmployeesUnrelatedWithProject(int projectId) {
        return this.employeeDao.findEmployeesUnrelatedWithProject(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Gender> findGenders() {
        return this.employeeDao.findGenders();
    }

    @Override
    @Transactional(readOnly = true)
    public WorkingPosition findWorkingPositionById(int workingPositionId) {
        return this.employeeDao.findWorkingPositionById(workingPositionId);
    }

    @Override
    @Transactional(readOnly = true)
    public Gender findGenderById(int genderId) {
        return this.employeeDao.findGenderById(genderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findProjectsUnrelatedWithEmployee(int employeeId) {
        return this.projectDao.findProjectsUnrelatedWithEmployee(employeeId);
    }

    @Override
    @Transactional
    public void saveComment(Comment comment) {
        this.commentDao.saveComment(comment);
    }

    @Override
    @Transactional
    public void savePost(Post post) {
        this.postDao.savePost(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Post> findPosts() {
        return this.postDao.findPosts();
    }

    @Override
    @Transactional(readOnly = true)
    public Post findPostById(int id) {
        return this.postDao.findPostById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAllEmployees() {
        return this.employeeDao.findAllEmployees();
    }

    @Override
    @Transactional(readOnly = true)
    public Long countPosts() {
        return this.postDao.countPosts();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findPosts(int page, int numberOfPosts) {
        return this.postDao.findPosts(page, numberOfPosts);
    }

    @Override
    @Transactional
    public void updateExperience(long experience, int employeeId) {
        this.employeeDao.updateExperience(experience, employeeId);
    }

    @Override
    @Transactional
    public void updateAge(long age, int employeeId) {
        this.employeeDao.updateAge(age, employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public TimeCounter getTimeCounterById(int counterId) {
        return this.timeCounterDao.getTimeCounterById(counterId);
    }

    @Override
    @Transactional
    public void updateDaysFromStart(long daysFromStart, int counterId) {
        this.timeCounterDao.updateDaysFromStart(daysFromStart, counterId);
    }

    @Override
    @Transactional
    public void deleteUserByUserId(int userId) {
        this.userDao.deleteUserByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteCommentsByUserId(int userId) {
     this.commentDao.deleteCommentsByUserId(userId);
    }

    @Override
    @Transactional
    public void updateEmployeePhoto(String filename, int employeeId) {
        this.employeeDao.updateEmployeePhoto(filename, employeeId);
    }

    @Override
    @Transactional
    public void updatePostImage(String filename, int postId) {
        this.postDao.updatePostImage(filename, postId);
    }

    @Override
    @Transactional
    public void deletePost(int postId) {
        this.postDao.deletePost(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAllProjects() {
        return this.projectDao.findAllProjects();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<User> findUsersByLastNameAndUserName(String lastName, String userName) {
        return this.userDao.findUsersByLastNameAndUserName(lastName, userName);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findEmployeeByIdFetchProjectsAndUser(int id) {
        return this.employeeDao.findEmployeeByIdFetchProjectsAndUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Project findProjectByIdFetchEmployees(int id) {
        return this.projectDao.findProjectByIdFetchEmployees(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Post findPostByIdFetchComments(int id) {
        return this.postDao.findPostByIdFetchComments(id);
    }

    @Override
    @Transactional
    public void updateAgeAndExpOfEmployees(TimeAnalyzer timeAnalyzer) {
        this.employeeDao.updateAgeAndExpOfEmployees(timeAnalyzer);
    }

    @Override
    @Transactional
    public void updateStatusesAndDaysLeftOfProjects(TimeAnalyzer timeAnalyzer) {
        this.projectDao.updateStatusesAndDaysLeftOfProjects(timeAnalyzer);
    }

    @Override
    @Transactional(readOnly = true)
    public Post findPostByIdFetchLikes(int id) {
        return this.postDao.findPostByIdFetchLikes(id);
    }

    @Override
    @Transactional
    public boolean didUserLikePost(int postId, int userId) {
        return this.likeDao.didUserLikePost(postId, userId);
    }

    @Override
    @Transactional
    public void saveLike(Like like) {
        this.likeDao.saveLike(like);
    }

    @Override
    @Transactional
    public void removeLikeFromPostByUserId(int userId, int postId) {
        this.likeDao.removeLikeFromPostByUserId(userId, postId);
    }

    @Override
    @Transactional
    public void updateAmountOfLikes(int postId, int amountOfLikes) throws DataAccessException {
        this.postDao.updateAmountOfLikes(postId, amountOfLikes);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findEmployeeByIdFetchUser(int id) throws DataAccessException {
        return this.employeeDao.findEmployeeByIdFetchUser(id);
    }
}
