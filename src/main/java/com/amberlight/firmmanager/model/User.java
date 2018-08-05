package com.amberlight.firmmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean object that represents a User.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */

@Entity
@Table(name = "users")
public class User extends Person{

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @JsonIgnore
    @Transient
    private String confirmPassword;

    @JsonIgnore
    @Transient
    private boolean createEmployeeOrNotFlag;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"  ),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isCreateEmployeeOrNotFlag() {
        return createEmployeeOrNotFlag;
    }

    public void setCreateEmployeeOrNotFlag(boolean createEmployeeOrNotFlag) {
        this.createEmployeeOrNotFlag = createEmployeeOrNotFlag;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", employee=" + employee +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", createEmployeeOrNotFlag=" + createEmployeeOrNotFlag +
                ", roles=" + roles +
                ", id=" + id +
                '}';
    }
}
