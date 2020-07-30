package com.amberlight.firmmanager.repository.jpa;

import com.amberlight.firmmanager.repository.ProjectDao;
import com.amberlight.firmmanager.model.Employee;
import com.amberlight.firmmanager.model.Project;
import com.amberlight.firmmanager.model.ProjectObjective;
import com.amberlight.firmmanager.model.ProjectStatus;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * JPA implementation of {@link ProjectDao} interface.
 */
@Repository("projectDao")
public class JpaProjectDaoImpl implements ProjectDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<Project> findProjectByNameAndProjectObjectiveAndProjectStatus(String name, String projectObjective, String projectStatus) {
        Query query = this.em.createQuery(
                "SELECT DISTINCT project FROM Project project " +
                "LEFT JOIN FETCH project.employees " +
                        "WHERE project.name LIKE :name " +
                        "AND project.projectObjective.name LIKE :projectObjective " +
                        "AND project.projectStatus.name LIKE :projectStatus");
        query.setParameter("name", name + "%");
        query.setParameter("projectObjective", projectObjective + "%");
        query.setParameter("projectStatus", projectStatus + "%");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ProjectObjective> findProjectObjectives() {
        return this.em.createQuery("SELECT projectObjective FROM ProjectObjective projectObjective ORDER BY projectObjective.name").getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ProjectStatus> findProjectStatuses() {
        return this.em.createQuery("SELECT projectStatus FROM ProjectStatus projectStatus ORDER BY projectStatus.name").getResultList();
    }

    @Override
    public Project findProjectById(int id) {
        try {
            return this.em.find(Project.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public ProjectStatus findProjectStatusById(int id) {
        return this.em.find(ProjectStatus.class, id);
    }

    @Override
    public ProjectObjective findProjectObjectiveById(int id) {
        return this.em.find(ProjectObjective.class, id);
    }

    @Override
    public void saveProject(Project project) {

        if (project.getId() == null) {
            this.em.persist(project);
        } else {
            this.em.merge(project);
        }
    }

    @Override
    public void removeProject(int id) {
        Query query = this.em.createNativeQuery("DELETE FROM projects_employees WHERE project_id = ?");
        query.setParameter(1, id);
        query.executeUpdate();
        Project project = this.em.find(Project.class,id);
        this.em.remove(project);
    }

    @Override
    public void detachEmployeeFromProject(int projectId, int employeeId) {

        Query query =  this.em.createNativeQuery("DELETE FROM projects_employees WHERE project_id = :project_id AND employee_id = :employee_id");

        query.setParameter("project_id", projectId);
        query.setParameter("employee_id", employeeId);

        query.executeUpdate();
    }

    @Override
    public void attachEmployeeToProject(int projectId, int employeeId) {
        Query query =  this.em.createNativeQuery("INSERT INTO projects_employees VALUES (:project_id,:employee_id);");

        query.setParameter("project_id", projectId);
        query.setParameter("employee_id", employeeId);

        query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Project> findProjectsUnrelatedWithEmployee(int employeeId) {
        Query query = this.em.createQuery(
                "SELECT DISTINCT project FROM Project project LEFT JOIN FETCH project.employees WHERE :employee NOT MEMBER OF project.employees");
        query.setParameter("employee", this.em.find(Employee.class, employeeId));
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Project> findAllProjects() {
        Query query = this.em.createQuery("SELECT DISTINCT project FROM Project project LEFT JOIN FETCH project.employees");
        return query.getResultList();
    }

    @Override
    public Project findProjectByIdFetchEmployees(int id) {
        Query query = this.em.createQuery("SELECT project FROM Project project " +
                "LEFT JOIN FETCH project.employees WHERE project.id = :id");
        query.setParameter("id", id);
        try {
            return (Project) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "JpaProjectDaoImpl{" +
                "em=" + em +
                '}';
    }
}


