package com.amberlight.firmmanager.model;


import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.util.*;

/**
 * A simple JavaBean object representing an employee.
 */
@Entity
@Table(name = "employees")
public class Employee extends Person implements Comparable<Employee> {

    @Column(name="birth_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @Transient
    private Long age;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "telephone")
    @Digits(fraction = 0, integer = 12, message = "This field may contain only maximum 12 digits (0-9).")
    private String telephone;

    @Column(name = "is_fired")
    private boolean fired;

    @Column(name = "hire_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date hireDate;

    @Transient
    private Double experience;

    @Column(name = "photo_filename")
    private String photoFileName;

    @Transient
    private String oldEmplPhotoName;

    @Transient
    private MultipartFile image;

    @Transient
    private Integer userIdToAttachWithEmpl;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "working_position_id")
    private WorkingPosition workingPosition;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "employee")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "employees")
    private Set<Project> projects;

    @Transient
    private Integer numberOfProjects;

    public Integer getNumberOfProjects() {
        return numberOfProjects;
    }

    public void setNumberOfProjects(Integer numberOfProjects) {
        this.numberOfProjects = numberOfProjects;
    }

    public Integer getUserIdToAttachWithEmpl() {
        return userIdToAttachWithEmpl;
    }

    public void setUserIdToAttachWithEmpl(Integer userIdToAttachWithEmpl) {
        this.userIdToAttachWithEmpl = userIdToAttachWithEmpl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOldEmplPhotoName() {
        return oldEmplPhotoName;
    }

    public void setOldEmplPhotoName(String oldEmplPhotoName) {
        this.oldEmplPhotoName = oldEmplPhotoName;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Double getExperience() {
        return experience;
    }

    public void setExperience(Double experience) {
        this.experience = experience;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }

    public WorkingPosition getWorkingPosition() {
        return workingPosition;
    }

    public void setWorkingPosition(WorkingPosition workingPosition) {
        this.workingPosition = workingPosition;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    protected Set<Project> getProjectsInternal() {
        if (this.projects == null) {
            this.projects = new HashSet<>();
        }
        return this.projects;
    }


    protected void setProjectsInternal(Set<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getProjects() {
        List<Project> projects = new ArrayList<>(getProjectsInternal());
        Collections.sort(projects);
        return projects;
    }

    public void addProject(Project project) {
        getProjectsInternal().add(project);
        project.addEmployee(this);
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public int compareTo(Employee employee) {
        return this.isFired()?1:-1;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "birthDate=" + birthDate +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", telephone='" + telephone + '\'' +
                ", fired=" + fired +
                ", hireDate=" + hireDate +
                ", experience=" + experience +
                ", photoFileName='" + photoFileName + '\'' +
                ", oldEmplPhotoName='" + oldEmplPhotoName + '\'' +
                ", image=" + image +
                ", userIdToAttachWithEmpl=" + userIdToAttachWithEmpl +
                ", gender=" + gender +
                ", workingPosition=" + workingPosition +
                ", user=" + user +
                ", projects=" + projects +
                ", id=" + id +
                '}';
    }
}
