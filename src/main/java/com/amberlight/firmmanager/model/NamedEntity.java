package com.amberlight.firmmanager.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * A simple JavaBean object representing a named entity.
 * Used as a base class for objects needing name property.
 */
@MappedSuperclass
public class NamedEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NamedEntity{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
