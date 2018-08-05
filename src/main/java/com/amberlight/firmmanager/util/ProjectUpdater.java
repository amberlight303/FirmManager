package com.amberlight.firmmanager.util;

import com.amberlight.firmmanager.model.Project;
import com.amberlight.firmmanager.model.ProjectStatus;


/**
 * An accessory class for {@link Project}.
 * Serves for calculating values of <code>Project</code>'s fields.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
public class ProjectUpdater {

    /**
     * Return <code>Project</code> after calculation and setting status and daysLeft fields.
     * The algorithm is:
     * For <code>Project</code> get <code>Project</code>'s status and
     * calculate the time difference between the end developing time of <code>Project</code>
     * and current time point. If this time difference is negative
     * and status is "In progress" or "Inactive", then set <code>Project</code>'s status as "Overdue".
     * If status is "In progress" or "Inactive" and time difference is positive (is days until the end),
     * then set daysLeft field with this time difference value.
     * @param project the <code>Project</code> to process
     * @return processed <code>Project</code>
     */
    public Project calcStatusAndDaysLeft(Project project){
        long currentTime = System.currentTimeMillis() / 1000;
        long endTime = project.getEndDate().getTime() / 1000;
        long timeDifference = endTime - currentTime;
        int statusId = project.getProjectStatus().getId();
        if ((statusId==1 || statusId==3) && timeDifference<=0) {
            ProjectStatus projectStatus = new ProjectStatus();
            projectStatus.setId(2);
            projectStatus.setName("Overdue");
            project.setProjectStatus(projectStatus);
        }
        if ((statusId==1 || statusId == 3) && timeDifference>0){
            project.setDaysLeft((int)timeDifference/86400);
        }
        return project;
    }


    @Override
    public String toString() {
        return "ProjectUpdater{}";
    }
}
