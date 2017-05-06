package com.amberlight.firmmanager.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * Simple JavaBean object adds a name property to <code>BaseEntity</code>. Used as a base class for objects
 * needing id property.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew(){
        return this.id==null;
    }
}

