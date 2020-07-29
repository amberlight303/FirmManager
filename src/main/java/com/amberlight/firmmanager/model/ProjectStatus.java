package com.amberlight.firmmanager.model;

import javax.persistence.*;

/**
 * A simple JavaBean object representing a project status of {@link Project}.
 */
@Entity
@Table(name = "project_statuses")
public class ProjectStatus extends NamedEntity{

}
