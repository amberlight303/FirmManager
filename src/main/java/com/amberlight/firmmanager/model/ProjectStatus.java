package com.amberlight.firmmanager.model;

import javax.persistence.*;

/**
 * Simple JavaBean object representing a project status of {@link Project}.
 */
@Entity
@Table(name = "project_statuses")
public class ProjectStatus extends NamedEntity{

}
