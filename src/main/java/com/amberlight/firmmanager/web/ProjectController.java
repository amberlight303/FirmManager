package com.amberlight.firmmanager.web;

import com.amberlight.firmmanager.model.Employee;
import com.amberlight.firmmanager.model.Project;
import com.amberlight.firmmanager.model.ProjectObjective;
import com.amberlight.firmmanager.model.ProjectStatus;
import com.amberlight.firmmanager.service.FirmManagerService;
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
        Project project = this.firmManagerService.findProjectById(projectId);
        if (project == null) return "test/oops";
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
        projectValidator.validate(project, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("project", project);
            return "projects/createOrUpdateProject";
        }
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
        this.firmManagerService.saveProject(project);
        return "redirect:/projects/{projectId}";
    }

    /**
     * Handling GET request for list of <code>Project</code>s and initiation of a searching form.
     */
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String initFindProjectsForm(Map<String, Object> model) {
        model.put("project", new Project());
        List<Project> results = this.firmManagerService.findAllProjects();
        Collections.sort(results);
        results.forEach( p -> {
            p.setDaysLeft((System.currentTimeMillis() - p.getEndDate().getTime()) / (1000 * 60 * 60 * 24));
        });
        model.put("selections", results);
        return "projects/projectsList";
    }

    /**
     * Handling GET request for searching <code>Project</code>s
     * by name, objective and status passed as a parameters.
     */
    @RequestMapping(value = "/projects/find", method = RequestMethod.GET)
    public String processFindProjectsForm(Project project/*, BindingResult result*/, Map<String, Object> model) {
        if (project.getName() == null) {
            project.setName("");
        }

        if (project.getProjectObjective().getName() == null) {
            project.getProjectObjective().setName("");
        }

        if (project.getProjectStatus().getName() == null) {
            project.getProjectStatus().setName("");
        }
        Collection<Project> results = this.firmManagerService
                .findProjectByNameAndProjectObjectiveAndProjectStatus(
                      project.getName(),
                        project.getProjectObjective().getName(),
                        project.getProjectStatus().getName()
                );

        results.forEach( p -> {
            p.setDaysLeft((System.currentTimeMillis() - p.getEndDate().getTime()) / (1000 * 60 * 60 * 24));
        });
        model.put("selections", results);
        return "projects/projectsList";
    }

    /**
     * Handling GET request for getting form for creating new <code>Project</code>.
     */
    @RequestMapping(value="/admin/projects/new", method = RequestMethod.GET)
    public String addProject(Model model) {
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
                                    Model model) {
        projectValidator.validate(project, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("project", project);
            return "projects/createOrUpdateProject";
        }
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
        project.setDaysLeft((System.currentTimeMillis() - project.getEndDate().getTime()) / (1000 * 60 * 60 * 24));
        if (project.getEmployees() != null) {
            project.getEmployees().forEach(employee -> {
                employee.setAge(((System.currentTimeMillis() - employee.getBirthDate().getTime()) / (1000 * 60 * 60 * 24)) / 365);
                employee.setExperience(((System.currentTimeMillis() - employee.getHireDate().getTime()) / (1000 * 60 * 60 * 24)) / 365.0);
            });
        }
        mav.addObject(project);
        return mav;
    }

    /**
     * This method will be called on form submission, handling POST request for
     * deleting a <code>Project</code> from the data store.
     */
    @RequestMapping(value = "/admin/projects/{projectId}/delete", method = RequestMethod.POST)
    public String deleteProject(@PathVariable("projectId") int projectId) {
        this.firmManagerService.removeProject(projectId);
        return "redirect:/projects";
    }

    /**
     * Handling POST request for deleting relation
     * between certain {@link Project} and {@link Employee}.
     */
    @RequestMapping(value="/admin/projects/{projectId}/employees/{employeeId}/detach", method = RequestMethod.POST)
    public String processDetachEmployee(@PathVariable("projectId") int projectId,
                                        @PathVariable("employeeId") int employeeId) {
        this.firmManagerService.detachEmployeeFromProject(projectId, employeeId);
        return "redirect:/projects/{projectId}";
    }

    /**
     * Handling POST request for creation relation
     * between certain {@link Project} and {@link Employee}.
     */
    @RequestMapping(value="/admin/projects/{projectId}/employees/{employeeId}/attach", method = RequestMethod.POST)
    public String processAttachEmployee(@PathVariable("projectId") int projectId,
                                        @PathVariable("employeeId") int employeeId) {
        this.firmManagerService.attachEmployeeToProject(projectId, employeeId);
        return "redirect:/admin/projects/{projectId}/employees/attach";
    }

    /**
     * Handling GET request for getting page with list of projects unrelated with certain
     * <code>Project</code> to attach <code>Employee</code>s to that <code>Project</code>.
     */
    @RequestMapping(value="/admin/projects/{projectId}/employees/attach", method = RequestMethod.GET)
    public String initAttachEmployee(@PathVariable("projectId") int projectId, Model model) {
        model.addAttribute("project", this.firmManagerService.findProjectById(projectId));
        List<Employee> selections = this.firmManagerService.findEmployeesUnrelatedWithProject(projectId);
        Collections.sort(selections);
        model.addAttribute("selections", selections);
        return "projects/attachEmployees";
    }

    @Override
    public String toString() {
        return "ProjectController{" +
                "firmManagerService=" + firmManagerService +
                ", projectValidator=" + projectValidator +
                '}';
    }
}
