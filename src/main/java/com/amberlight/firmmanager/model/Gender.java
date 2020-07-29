package com.amberlight.firmmanager.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A simple JavaBean object representing a gender of {@link Employee}.
 */
@Entity
@Table(name = "genders")
public class Gender extends NamedEntity {
}
