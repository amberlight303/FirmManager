package com.amberlight.firmmanager.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A simple JavaBean object that represents a working position of {@link Employee}.
 */
@Entity
@Table(name = "working_positions")
public class WorkingPosition extends NamedEntity{
}
