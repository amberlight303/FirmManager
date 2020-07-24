package com.amberlight.firmmanager.model;


import javax.persistence.*;
import java.util.Set;

/**
 * Simple JavaBean object representing role of {@link User}.
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
                "users=" + users +
                ", id=" + id +
                '}';
    }
}
