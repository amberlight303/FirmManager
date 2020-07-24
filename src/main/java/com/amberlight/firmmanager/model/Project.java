package com.amberlight.firmmanager.model;


import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.*;

/**
 * Simple JavaBean object representing a project.
 */
@Entity
@Table(name = "projects")
public class Project extends NamedEntity implements Comparable<Project> {

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date endDate;
    
    @Column(name = "description")
    private String description;

    @Column(name = "notes")
    private String notes;

    @Column(name = "days_left")
    private int daysLeft;

    @ManyToOne
    @JoinColumn(name = "project_status_id")
    private ProjectStatus projectStatus;

    @ManyToOne
    @JoinColumn(name = "project_objective_id")
    private ProjectObjective projectObjective;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "projects_employees", joinColumns = @JoinColumn(name = "project_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> employees;

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectObjective getProjectObjective() {
        return projectObjective;
    }

    public void setProjectObjective(ProjectObjective projectObjective) {
        this.projectObjective = projectObjective;
    }

    protected Set<Employee> getEmployeesInternal(){
        if(this.employees == null){
            this.employees = new HashSet<>();
        }
        return this.employees;
    }


    protected void setEmployeesInternal(Set<Employee> employees){
        this.employees = employees;
    }


    public List<Employee> getEmployees(){
        List<Employee> employees = new ArrayList<>(getEmployeesInternal());
        Collections.sort(employees);
        return employees;
    }

    public void addEmployee(Employee employee){
        getEmployeesInternal().add(employee);
        employee.addProject(this);
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }


    @Override
    public int compareTo(Project otherProject) {
        int result;
        int myProjectStatus = this.getProjectStatus().getId();
        int otherProjectStatus = otherProject.getProjectStatus().getId();
        result = Integer.compare(myProjectStatus, otherProjectStatus);
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", notes='" + notes + '\'' +
                ", daysLeft=" + daysLeft +
                ", projectStatus=" + projectStatus +
                ", projectObjective=" + projectObjective +
                ", employees=" + employees +
                ", id=" + id +
                '}';
    }
}
