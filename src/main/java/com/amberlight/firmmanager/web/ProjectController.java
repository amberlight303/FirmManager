package com.amberlight.firmmanager.web;

import com.amberlight.firmmanager.model.Employee;
import com.amberlight.firmmanager.model.Project;
import com.amberlight.firmmanager.model.ProjectObjective;
import com.amberlight.firmmanager.model.ProjectStatus;
import com.amberlight.firmmanager.service.FirmManagerService;
import com.amberlight.firmmanager.util.ProjectUpdater;
import com.amberlight.firmmanager.util.TimeAnalyzer;
import com.amberlight.firmmanager.validator.ProjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

/**
 * A controller for pages related with the {@link Project} entity.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
@Controller
public class ProjectController {

    @Autowired
    private FirmManagerService firmManagerService;

    @Autowired
    private ProjectValidator projectValidator;

    /**
     * Populate {@link ProjectStatus}es for controllers.
     * In particular for editing <code>Project</code>s.
     * @return a list of <code>ProjectStatus</code>s
     */
    @ModelAttribute("projectStatuses")
    public List<ProjectStatus> populateProjectStatuses() {

        return this.firmManagerService.findProjectStatuses();
    }

    /**
     * Populate {@link ProjectObjective}s for controllers.
     * In particular for editing <code>Project</code>s.
     * @return a list of <code>ProjectObjective</code>s
     */
    @ModelAttribute("projectObjectives")
    public List<ProjectObjective> populateProjectObjectives() {

        return this.firmManagerService.findProjectObjectives();
    }

    /**
     * Handling GET request for getting an edit form for a particular <code>Project</code>.
     */
    @RequestMapping(value = "/admin/projects/{projectId}/edit", method = RequestMethod.GET)
    public String initUpdateProjectForm(@PathVariable("projectId") int projectId, ModelMap model) {
        //get the Project for editing
        Project project = this.firmManagerService.findProjectById(projectId);
        if (project == null) return "test/oops";
        //code for keeping days left to the end field of each project always actual
        TimeAnalyzer timeAnalyzer = new TimeAnalyzer();
        //there is passing the second TimeCounter object
        // from the data store special for keeping data of projects actual
        long daysDifference = timeAnalyzer.hasDayPassed(firmManagerService.getTimeCounterById(2));
        //if 1 day has been passed
        if (daysDifference != 0) {
            //update days from start of the second in data base
            // TimeCounter object that serves for projects
            firmManagerService.updateDaysFromStart(daysDifference, 2);
            //update days left to the end field of all projects
            this.firmManagerService.updateStatusesAndDaysLeftOfProjects(timeAnalyzer);
        }

        model.put("project", project);
        return "projects/createOrUpdateProject";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating an <code>Project</code> in the data store. It also validates the user input.
     */
    @RequestMapping(value = "/admin/projects/{projectId}/edit", method = RequestMethod.POST)
    public String processUpdateProjectForm(@PathVariable("projectId") int projectId,
                                           @Valid Project project,
                                           BindingResult bindingResult,
                                           ModelMap model) {

        //validate an inputted project
        projectValidator.validate(project, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("project", project);
            return "projects/createOrUpdateProject";
        }
        //status and objective options was sent to the edit form as hmtl select options
        // and project's status and objective fields was set only with id's
        // of particular status and objective object
        //for correct persistence all fields should be set, so below code does fetching full
        // ProjectStatus and ProjectObjective objects from DB and properly
        // setting an project's respective fields
        int projectStatusId = project.getProjectStatus().getId();
        int projectObjectiveId = project.getProjectObjective().getId();
        ProjectStatus projectStatus = this.firmManagerService.findProjectStatusById(projectStatusId);
        ProjectObjective projectObjective = this.firmManagerService.findProjectObjectiveById(projectObjectiveId);
        project.getProjectStatus().setName(projectStatus.getName());
        project.getProjectObjective().setName(projectObjective.getName());
        Set<Employee> employeeSet = new HashSet<>();
        //add to the submitted Project all employees by fetching them from the data store
        employeeSet.addAll(this.firmManagerService.findProjectByIdFetchEmployees(projectId).getEmployees());
        project.setEmployees(employeeSet);
        ProjectUpdater projectUpdater = new ProjectUpdater();
        //calculate an set actual values of status and days left fields based
        // on old status and the project's developing end time
        project = projectUpdater.calcStatusAndDaysLeft(project);
        this.firmManagerService.saveProject(project);
        return "redirect:/projects/{projectId}";
    }

    /**
     * Handling GET request for list of <code>Project</code>s and initiation of a searching form.
     */
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String initFindProjectsForm(Map<String, Object> model){

        //code for keeping days left to the end field of each project always actual
        TimeAnalyzer timeAnalyzer = new TimeAnalyzer();
        //there is passing the second TimeCounter object
        // from the data store special for keeping data of projects actual
        long daysDifference = timeAnalyzer.hasDayPassed(firmManagerService.getTimeCounterById(2));
        //if 1 day has been passed
        if (daysDifference != 0) {
            //update days from start of the second in data base
            // TimeCounter object that serves for projects
            firmManagerService.updateDaysFromStart(daysDifference, 2);
            //update days left to the end field of all projects
            this.firmManagerService.updateStatusesAndDaysLeftOfProjects(timeAnalyzer);
        }
        model.put("project", new Project());
        List<Project> results = this.firmManagerService.findAllProjects();
        Collections.sort(results);
        model.put("selections", results);

        return "projects/projectsList";
    }

    /**
     * Handling GET request for searching <code>Project</code>s
     * by name, objective and status passed as a parameters.
     */
    @RequestMapping(value = "/projects/find", method = RequestMethod.GET)
    public String processFindProjectsForm(Project project/*, BindingResult result*/, Map<String, Object> model) {

        //code for keeping days left to the end field of each project always actual
        TimeAnalyzer timeAnalyzer = new TimeAnalyzer();
        //there is passing the second TimeCounter object
        // from the data store special for keeping data of projects actual
        long daysDifference = timeAnalyzer.hasDayPassed(firmManagerService.getTimeCounterById(2));
        //if 1 day has been passed
        if (daysDifference != 0) {
            //update days from start of the second in data base
            // TimeCounter object that serves for projects
            firmManagerService.updateDaysFromStart(daysDifference, 2);
            //update days left to the end field of all projects
            this.firmManagerService.updateStatusesAndDaysLeftOfProjects(timeAnalyzer);
        }
        if (project.getName() == null) {
            project.setName(""); // empty string signifies broadest possible search
        }

        if (project.getProjectObjective().getName() == null) {
            project.getProjectObjective().setName(""); // empty string signifies broadest possible search
        }

        if (project.getProjectStatus().getName() == null) {
            project.getProjectStatus().setName(""); // empty string signifies broadest possible search
        }

        // find projects by name, project's objective and project's status
        Collection<Project> results = this.firmManagerService
                .findProjectByNameAndProjectObjectiveAndProjectStatus(
                      project.getName(),
                        project.getProjectObjective().getName(),
                        project.getProjectStatus().getName()
                );
        model.put("selections", results);
        return "projects/projectsList";
    }

    /**
     * Handling GET request for getting form for creating new <code>Project</code>.
     */
    @RequestMapping(value="/admin/projects/new", method = RequestMethod.GET)
    public String addProject(Model model){
        model.addAttribute("project", new Project());
        return "projects/createOrUpdateProject";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * creating a new <code>Project</code> in the data store.
     */
    @RequestMapping(value="/admin/projects/new", method = RequestMethod.POST)
    public String processAddProject(@Valid Project project,
                                    BindingResult bindingResult,
                                    Model model){
        //validate an inputted project
        projectValidator.validate(project, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("project", project);
            return "projects/createOrUpdateProject";
        }
        //calculate an set actual values of status and days left fields based
        // on old status and the project's developing end time
        project = new ProjectUpdater().calcStatusAndDaysLeft(project);
        this.firmManagerService.saveProject(project);
        return "redirect:/projects";
    }

    /**
     * Handling GET request for a particular <code>Project</code>.
     */
    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.GET)
    public ModelAndView showProject(@PathVariable("projectId") int projectId) {
        ModelAndView mav = new ModelAndView("projects/projectDetails");
        Project project = this.firmManagerService.findProjectByIdFetchEmployees(projectId);
        if (project == null) {
            mav.setViewName("test/oops");
            return mav;
        }
        //code for keeping days left to the end field of each project always actual
        TimeAnalyzer timeAnalyzer = new TimeAnalyzer();
        //there is passing the second TimeCounter object
        // from the data store special for keeping data of projects actual
        long daysDifference = timeAnalyzer.hasDayPassed(firmManagerService.getTimeCounterById(2));
        //if 1 day has been passed
        if (daysDifference != 0) {
            //update days from start of the second in data base
            // TimeCounter object that serves for projects
            firmManagerService.updateDaysFromStart(daysDifference, 2);
            //update days left to the end field of all projects
            this.firmManagerService.updateStatusesAndDaysLeftOfProjects(timeAnalyzer);
            project = this.firmManagerService.findProjectByIdFetchEmployees(projectId);
        }
        mav.addObject(project);
        return mav;
    }

    /**
     * This method will be called on form submission, handling POST request for
     * deleting a <code>Project</code> from the data store.
     */
    @RequestMapping(value = "/admin/projects/{projectId}/delete", method = RequestMethod.POST)
    public String deleteProject(@PathVariable("projectId") int projectId){
        this.firmManagerService.removeProject(projectId);
        return "redirect:/projects";
    }

    /**
     * Handling POST request for deleting relation
     * between certain {@link Project} and {@link Employee}.
     */
    @RequestMapping(value="/admin/projects/{projectId}/employees/{employeeId}/detach", method = RequestMethod.POST)
    public String processDetachEmployee(@PathVariable("projectId") int projectId,
                                        @PathVariable("employeeId") int employeeId){
        this.firmManagerService.detachEmployeeFromProject(projectId, employeeId);
        return "redirect:/projects/{projectId}";
    }

    /**
     * Handling POST request for creation relation
     * between certain {@link Project} and {@link Employee}.
     */
    @RequestMapping(value="/admin/projects/{projectId}/employees/{employeeId}/attach", method = RequestMethod.POST)
    public String processAttachEmployee(@PathVariable("projectId") int projectId,
                                        @PathVariable("employeeId") int employeeId){
        this.firmManagerService.attachEmployeeToProject(projectId, employeeId);
        return "redirect:/admin/projects/{projectId}/employees/attach";
    }

    /**
     * Handling GET request for getting page with list of projects unrelated with certain
     * <code>Project</code> to attach <code>Employee</code>s to that <code>Project</code>.
     */
    @RequestMapping(value="/admin/projects/{projectId}/employees/attach", method = RequestMethod.GET)
    public String initAttachEmployee(@PathVariable("projectId") int projectId, Model model){
        //code for keeping age and experience of each employee always actual
        TimeAnalyzer timeAnalyzer = new TimeAnalyzer();
        //there is passing the first TimeCounter object
        // from the data store special for keeping data of employees actual
        long daysDifference = timeAnalyzer.hasDayPassed(firmManagerService.getTimeCounterById(1));
        //if 1 day has been passed
        if (daysDifference != 0) {
            //update days from start of first in data base
            // TimeCounter object that serves for employees
            firmManagerService.updateDaysFromStart(daysDifference, 1);
            //update ages and experiences of all employees
            this.firmManagerService.updateAgeAndExpOfEmployees(timeAnalyzer);
        }
        model.addAttribute("project", this.firmManagerService.findProjectById(projectId));
        List<Employee> selections = this.firmManagerService.findEmployeesUnrelatedWithProject(projectId);
        Collections.sort(selections);
        model.addAttribute("selections", selections);
        return "projects/attachEmployees";
    }
}
