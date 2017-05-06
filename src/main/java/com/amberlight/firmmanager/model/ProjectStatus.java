package com.amberlight.firmmanager.model;

import javax.persistence.*;

/**
 * Simple JavaBean object representing a project status of {@link Project}.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@Entity
@Table(name = "project_statuses")
public class ProjectStatus extends NamedEntity{

}
