package com.amberlight.firmmanager.model;


import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean object representing role of {@link User}.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */

@Entity
@Table(name = "roles")
public class Role extends NamedEntity{


    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + this.getName() + '\'' +
                ", users=" + this.getUsers() +
                '}';
    }
}
