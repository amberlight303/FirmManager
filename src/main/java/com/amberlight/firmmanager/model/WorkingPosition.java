package com.amberlight.firmmanager.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Simple JavaBean object that represents a working position of {@link Employee}.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@Entity
@Table(name = "working_positions")
public class WorkingPosition extends NamedEntity{
}