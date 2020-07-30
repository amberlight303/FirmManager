package com.amberlight.firmmanager.repository.jpa;

import com.amberlight.firmmanager.repository.EmployeeDao;
import com.amberlight.firmmanager.model.Employee;
import com.amberlight.firmmanager.model.Gender;
import com.amberlight.firmmanager.model.Project;
import com.amberlight.firmmanager.model.WorkingPosition;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * JPA implementation of {@link EmployeeDao} interface.
 */
@Repository("employeeDao")
public class JpaEmployeeDaoImpl implements EmployeeDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> findEmployeeByLastNameAndWorkingPosition(String lastName, String workingPosition) {
        Query query = this.em.createQuery(
                "SELECT DISTINCT employee FROM Employee employee " +
                        "LEFT JOIN FETCH employee.projects " +
                        "WHERE employee.lastName LIKE :lastName " +
                        "AND employee.workingPosition.name LIKE :workingPosition");
        query.setParameter("lastName", lastName + "%");
        query.setParameter("workingPosition", workingPosition + "%");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<WorkingPosition> findWorkingPositions() {
        return this.em.createQuery("SELECT workingPosition FROM WorkingPosition workingPosition ORDER BY workingPosition.name").getResultList();

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Gender> findGenders() {
        return this.em.createQuery("SELECT gender FROM Gender gender ORDER BY gender.name").getResultList();
    }

    @Override
    public Employee findEmployeeById(int id) {
        try {
            return this.em.find(Employee.class, id);
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void saveEmployee(Employee employee) {
        if (employee.getId() == null) {
            this.em.persist(employee);
        } else {
            this.em.merge(employee);
        }
    }

    @Override
    public WorkingPosition findWorkingPositionById(int workingPositionId) {
        return this.em.find(WorkingPosition.class, workingPositionId);
    }

    @Override
    public Gender findGenderById(int genderId) {
        return this.em.find(Gender.class, genderId);
    }

    @Override
    public void removeEmployee(int employeeId) {
        Query query =  this.em.createNativeQuery("DELETE FROM projects_employees WHERE employee_id = :employee_id");

        query.setParameter("employee_id", employeeId);
        query.executeUpdate();

        Employee employee = this.em.find(Employee.class,employeeId);
        this.em.remove(employee);
    }

    @Override
    public void detachProjectFromEmployee(int projectId, int employeeId) {

        Query query =  this.em.createNativeQuery("DELETE FROM projects_employees WHERE project_id = :project_id AND employee_id = :employee_id");

        query.setParameter("project_id", projectId);
        query.setParameter("employee_id", employeeId);

        query.executeUpdate();
    }

    @Override
    public void attachProjectToEmployee(int projectId, int employeeId) {
        Query query =  this.em.createNativeQuery("INSERT INTO projects_employees VALUES (:project_id,:employee_id);");

        query.setParameter("project_id", projectId);
        query.setParameter("employee_id", employeeId);

        query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> findEmployeesUnrelatedWithProject(int projectId) {
        Query query = this.em.createQuery(
                "SELECT DISTINCT employee FROM Employee employee LEFT JOIN FETCH employee.projects WHERE :project NOT MEMBER OF employee.projects");
        query.setParameter("project", this.em.find(Project.class, projectId));
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Employee> findAllEmployees() {
        Query query = this.em.createQuery("SELECT DISTINCT employee FROM Employee employee " +
                "LEFT JOIN FETCH employee.projects");
        return query.getResultList();
    }

    @Override
    public void updateEmployeePhoto(String filename, int employeeId) {
        Query query = this.em.createNativeQuery("UPDATE employees SET photo_filename = ? WHERE id = ?");
        query.setParameter(1, filename);
        query.setParameter(2, employeeId);
        query.executeUpdate();
    }

    @Override
    public Employee findEmployeeByIdFetchProjectsAndUser(int id) {

            Query query = this.em.createQuery("SELECT employee FROM Employee employee " +
                    "LEFT JOIN FETCH employee.projects LEFT JOIN FETCH employee.user WHERE employee.id = :id");
            query.setParameter("id", id);
        try {
            return (Employee) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public Employee findEmployeeByIdFetchUser(int id) throws DataAccessException {
        Query query = this.em.createQuery("SELECT employee FROM Employee employee " +
                "LEFT JOIN FETCH employee.user WHERE employee.id = :id");
        query.setParameter("id", id);
        try {
            return (Employee) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "JpaEmployeeDaoImpl{" +
                "em=" + em +
                '}';
    }
}


