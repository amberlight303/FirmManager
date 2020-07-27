package com.amberlight.firmmanager.web;

import com.amberlight.firmmanager.model.*;
import com.amberlight.firmmanager.service.FirmManagerService;
import com.amberlight.firmmanager.service.UserService;
import com.amberlight.firmmanager.util.*;
import com.amberlight.firmmanager.validator.EmployeeValidator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.*;

/**
 * A controller for pages related with the {@link Employee} entity.
 */
@Controller
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private FirmManagerService firmManagerService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeValidator employeeValidator;

    @Value("${uploads.rootPath}")
    private String uploadRootPath;

    /**
     * Populate <code>Gender</code>s for controllers.
     * In particular for editing <code>Employee</code>s.
     * @return a list of <code>Gender</code>s
     */
    @ModelAttribute("genders")
    public List<Gender> populateGenders() {
        return this.firmManagerService.findGenders();
    }

    /**
     * Populate <code>WorkingPosition</code>s for controllers.
     * In particular for editing <code>Employee</code>s.
     * @return a list of <code>WorkingPosition</code>s
     */
    @ModelAttribute("workingPositions")
    public List<WorkingPosition> populateWorkingPositions() {
        return this.firmManagerService.findWorkingPositions();
    }

    /**
     * Handling GET request for list of <code>Employee</code>s and initiation of a searching form.
     */
    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public String initFindEmployeesForm(Model model) {
        model.addAttribute("employee", new Employee());
        List<Employee> employees = this.firmManagerService.findAllEmployees();
        Collections.sort(employees);
        employees.forEach( employee -> {
            employee.setAge(((System.currentTimeMillis() - employee.getBirthDate().getTime()) / (1000 * 60 * 60 * 24)) / 365);
            employee.setExperience(((System.currentTimeMillis() - employee.getHireDate().getTime()) / (1000 * 60 * 60 * 24)) / 365.0);
        });

        model.addAttribute("selections", employees);
        return "employees/employeesList";
    }

    /**
     * Handling GET request for searching <code>Employee</code>s
     * by last name and working position passed as a parameters.
     */
    @RequestMapping(value = "/employees/find", method = RequestMethod.GET)
    public String processFindEmployeesForm(Employee employee/*, BindingResult result*/, Map<String, Object> model) {
        if (employee.getLastName() == null) {
            employee.setLastName("");
        }
        if (employee.getWorkingPosition().getName() == null) {
            employee.getWorkingPosition().setName("");
        }
        Collection<Employee> result = this.firmManagerService
                .findEmployeeByLastNameAndWorkingPosition(
                        employee.getLastName(),
                        employee.getWorkingPosition().getName()
                );
        result.forEach( e -> {
            e.setAge(((System.currentTimeMillis() - e.getBirthDate().getTime()) / (1000 * 60 * 60 * 24)) / 365);
            e.setExperience(((System.currentTimeMillis() - e.getHireDate().getTime()) / (1000 * 60 * 60 * 24)) / 365.0);
        });
        model.put("selections", result);
        return "employees/employeesList";
    }

    /**
     * Handling GET request for a particular <code>Employee</code>.
     */
    @RequestMapping(value = "/employees/{employeeId}", method = RequestMethod.GET)
    public String showEmployee(@PathVariable("employeeId") int employeeId, Model model, HttpServletRequest request) {
        Employee employee = this.firmManagerService.findEmployeeByIdFetchProjectsAndUser(employeeId);
        if (employee == null) {
            return "test/oops";
        }
        if (employee.getPhotoFileName()==null) {
            model.addAttribute("emptyPhotoName", "noPhoto.png");
        } else {
            model.addAttribute("photoName", "employeesPhotos"
                    + File.separator
                    + employee.getPhotoFileName());
        }
        employee.setAge(((System.currentTimeMillis() - employee.getBirthDate().getTime()) / (1000 * 60 * 60 * 24)) / 365);
        employee.setExperience(((System.currentTimeMillis() - employee.getHireDate().getTime()) / (1000 * 60 * 60 * 24)) / 365.0);
        model.addAttribute("employee", employee);
        return "employees/employeeDetails";
    }

    /**
     * Handling GET request for getting edit form for a particular <code>Employee</code>.
     */
    @RequestMapping(value = "/admin/employees/{employeeId}/edit", method = RequestMethod.GET)
    public String initUpdateEmployeeForm(@PathVariable("employeeId") int employeeId,
                                         ModelMap model) {
        Employee employee = this.firmManagerService.findEmployeeById(employeeId);
        if (employee == null) {
            return "test/oops";
        }
        if (employee.getPhotoFileName()==null) {
            model.addAttribute("emptyPhotoName", "noPhoto.png");
        } else {
            model.addAttribute("photoName", "employeesPhotos"
                    + File.separator
                    + employee.getPhotoFileName());
        }
        model.put("employee", employee);
        return "employees/createOrUpdateEmployee";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * updating an <code>Employee</code> in the data store. It also validates the user input.
     */
    @RequestMapping(value = "/admin/employees/{employeeId}/edit", method = RequestMethod.POST)
    public String processUpdateEmployeeForm(@PathVariable("employeeId") int employeeId,
                                            @Valid Employee employee,
                                            BindingResult bindingResult,
                                            ModelMap model)  {
        employeeValidator.validate(employee, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employee);
            if (employee.getPhotoFileName()==null) {
                model.addAttribute("emptyPhotoName", "noPhoto.png");
            } else {
                model.addAttribute("photoName", "employeesPhotos"
                        + File.separator
                        + employee.getPhotoFileName());
            }
            return "employees/createOrUpdateEmployee";
        }
        int genderId = employee.getGender().getId();
        int workingPositionId = employee.getWorkingPosition().getId();
        Gender gender = this.firmManagerService.findGenderById(genderId);
        WorkingPosition workingPosition = this.firmManagerService.findWorkingPositionById(workingPositionId);
        employee.getWorkingPosition().setName(workingPosition.getName());
        employee.getGender().setName(gender.getName());
        Set<Project> projectSet = new HashSet<>();
        projectSet.addAll(this.firmManagerService.findEmployeeByIdFetchProjectsAndUser(employeeId).getProjects());
        employee.setProjects(projectSet);
        if (!employee.getImage().isEmpty()) {
            try {
                byte[] bytes = employee.getImage().getBytes();
                String rootPath = this.uploadRootPath
                        + File.separator
                        + "employeesPhotos";
                if (employee.getOldEmplPhotoName()!=null) {
                    File oldPostPhoto = new File(rootPath + File.separator + employee.getOldEmplPhotoName());
                    oldPostPhoto.delete();
                }
                File directory = new File(rootPath);
                if (!directory.exists())
                    directory.mkdirs();
                RandomStringCreator randomStringCreator = new RandomStringCreator();
                File newServerFile;
                String fileName;
                String fullFileName;
                FilesAnalyzer filesAnalyzer = new FilesAnalyzer();
                do {
                    fileName = randomStringCreator.getRandomString(7);
                    fullFileName = fileName + "." + FilenameUtils.getExtension(employee.getImage().getOriginalFilename());
                    newServerFile = new File(directory.getAbsolutePath()
                            + File.separator + fullFileName);
                } while (filesAnalyzer.doDirectoryHaveFile(
                        fullFileName,
                        directory));
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(newServerFile));
                stream.write(bytes);
                stream.close();
                employee.setPhotoFileName(fullFileName);
            } catch (Exception e) {
                e.printStackTrace();
                return "test/oops";
            }
        } else {
            employee.setPhotoFileName(this.firmManagerService.findEmployeeById(employeeId).getPhotoFileName());
        }
        employee.setImage(null);
        this.firmManagerService.saveEmployee(employee);
        return "redirect:/employees/{employeeId}";
    }

    /**
     * Handling GET request for getting form for creating new <code>Employee</code>.
     * @param userForm this parameter should pass from registration controller
     *                 as redirect flash attribute, this object contains a first and last name
     *                 for <code>Employee</code> who will attach with the user,
     *                 created from the previous registration page.
     */
    @RequestMapping(value="/admin/employees/new", method = RequestMethod.GET)
    public String addEmployee(Model model, @ModelAttribute("userForm") User userForm ) {
        if (userForm != null && userForm.getUsername() != null) {
            Employee employee = new Employee();
            employee.setFirstName(userForm.getFirstName());
            employee.setLastName(userForm.getLastName());
            employee.setUserIdToAttachWithEmpl(userForm.getId());
            model.addAttribute("emptyPhotoName", "noPhoto.png");
            model.addAttribute("employee", employee);
            model.addAttribute("message", "Create new employee attached with the user "
                    + userForm.getUsername());
        } else {
            model.addAttribute("emptyPhotoName", "noPhoto.png");
            model.addAttribute("employee", new Employee());
        }
        return "employees/createOrUpdateEmployee";
    }

    /**
     * This method will be called on form submission, handling POST request for
     * creating a new <code>Employee</code> in the data store. Method binds {@link User}
     * with just created <code>Employee</code>, if the value of <code>User</code>'s id has been passed
     * to <code>Employee</code>'s object through EL (expression language) and hidden
     * input from the creation page. It also validates the user input.
     */
    @RequestMapping(value="/admin/employees/new", method = RequestMethod.POST)
    public String processAddEmployee(@Valid Employee employee,
                                     BindingResult bindingResult,
                                     Model model) {
        employeeValidator.validate(employee, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employee);
            model.addAttribute("emptyPhotoName", "noPhoto.png");
            return "employees/createOrUpdateEmployee";
        }
        int genderId = employee.getGender().getId();
        int workingPositionId = employee.getWorkingPosition().getId();
        Gender gender = this.firmManagerService.findGenderById(genderId);
        WorkingPosition workingPosition = this.firmManagerService.findWorkingPositionById(workingPositionId);
        employee.getWorkingPosition().setName(workingPosition.getName());
        employee.getGender().setName(gender.getName());
        if (!employee.getImage().isEmpty()) {
            try {
                byte[] bytes = employee.getImage().getBytes();
                String rootPath = this.uploadRootPath
                        + File.separator
                        + "employeesPhotos";
                if (employee.getOldEmplPhotoName()!=null) {
                    File oldPostPhoto = new File(rootPath + File.separator + employee.getOldEmplPhotoName());
                    oldPostPhoto.delete();
                }
                File directory = new File(rootPath);
                if (!directory.exists())
                    directory.mkdirs();
                RandomStringCreator randomStringCreator = new RandomStringCreator();
                File newServerFile;
                String fileName;
                String fullFileName;
                FilesAnalyzer filesAnalyzer = new FilesAnalyzer();
                do{
                    fileName = randomStringCreator.getRandomString(7);
                    fullFileName = fileName+ "." + FilenameUtils.getExtension(employee.getImage().getOriginalFilename());
                    newServerFile = new File(directory.getAbsolutePath()
                            + File.separator + fullFileName);
                } while (filesAnalyzer.doDirectoryHaveFile(fullFileName, directory));
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(newServerFile));
                stream.write(bytes);
                stream.close();
                employee.setPhotoFileName(fullFileName);
                employee.setImage(null);
                this.firmManagerService.saveEmployee(employee);
                if (employee.getUserIdToAttachWithEmpl() != 0) {
                    this.userService.attachUserToEmployee(employee.getUserIdToAttachWithEmpl(), employee.getId());
                }
                return "redirect:/employees/" + employee.getId();
            } catch (Exception e) {
                e.printStackTrace();
                return "test/oops";
            }
        }
        this.firmManagerService.saveEmployee(employee);
        if (employee.getUserIdToAttachWithEmpl() != 0) {
            this.userService.attachUserToEmployee(employee.getUserIdToAttachWithEmpl(), employee.getId());
        }
        return "redirect:/employees/" + employee.getId();
    }

    /**
     * This method will be called on form submission, handling POST request for
     * deleting an <code>Employee</code> from the data store.
     */
    @RequestMapping(value = "/admin/employees/{employeeId}/delete", method = RequestMethod.POST)
    public String deleteEmployee(@PathVariable("employeeId") int employeeId) {
        Employee employee = this.firmManagerService.findEmployeeByIdFetchUser(employeeId);
        if (employee == null) return "test/oops";
        if (employee.getPhotoFileName() != null) {
            File photoLocation = new File(this.uploadRootPath
                    + File.separator + "employeesPhotos" + File.separator + employee.getPhotoFileName());
            photoLocation.delete();
        }
        if (employee.getUser() != null) {
            this.firmManagerService.deleteCommentsByUserId(employee.getUser().getId());
        }
        if (employee.getUser() != null) {
            this.firmManagerService.deleteUserByUserId(employee.getUser().getId());
        }
        this.firmManagerService.removeEmployee(employeeId);
        return "redirect:/employees";
    }

    /**
     * Handling POST request for deleting relation
     * between certain {@link Employee} and {@link Project}.
     */
    @RequestMapping(value="/admin/employees/{employeeId}/projects/{projectId}/detach", method = RequestMethod.POST)
    public String processDetachProject(@PathVariable("projectId") int projectId,
                                        @PathVariable("employeeId") int employeeId) {

        this.firmManagerService.detachProjectFromEmployee(projectId, employeeId);
        return "redirect:/employees/{employeeId}";
    }

    /**
     * Handling POST request for creation relation
     * between certain {@link Employee} and {@link Project}.
     */
    @RequestMapping(value="/admin/employees/{employeeId}/projects/{projectId}/attach", method = RequestMethod.POST)
    public String processAttachProject(@PathVariable("projectId") int projectId,
                                        @PathVariable("employeeId") int employeeId) {

        this.firmManagerService.attachProjectToEmployee(projectId, employeeId);
        return "redirect:/admin/employees/{employeeId}/projects/attach";
    }

    /**
     * Handling GET request for getting page with list of projects unrelated with certain
     * <code>Employee</code> to attach <code>Project</code>s to that <code>Employee</code>.
     */
    @RequestMapping(value="/admin/employees/{employeeId}/projects/attach", method = RequestMethod.GET)
    public String initAttachProject(@PathVariable("employeeId") int employeeId, Model model) {
        model.addAttribute("employee", this.firmManagerService.findEmployeeById(employeeId));
        List<Project> selections = this.firmManagerService.findProjectsUnrelatedWithEmployee(employeeId);
        Collections.sort(selections);
        model.addAttribute("selections", selections);
        return "employees/attachProjects";
    }

    /**
     * Handling post request for setting an Employee as fired
     */
    @RequestMapping(value = "/admin/employees/{employeeId}/fire", method = RequestMethod.POST)
    public String processFireEmployee(@PathVariable("employeeId") int employeeId) {
        Employee employee = this.firmManagerService.findEmployeeByIdFetchProjectsAndUser(employeeId);
        employee.setFired(true);
        this.firmManagerService.saveEmployee(employee);
        return "redirect:/employees/{employeeId}";
    }

    /**
     * Handling get request for downloading an Employee's photo
     */
    @RequestMapping(value = "/employees/{employeeId}/download-photo", method = RequestMethod.GET)
    public String processDownloadEmplPhoto(@PathVariable("employeeId") int employeeId,
                                         HttpServletResponse response) throws IOException {
        Employee employee = this.firmManagerService.findEmployeeById(employeeId);
        if (employee.getPhotoFileName() == null) return "test/oops";
            File photoInFilesSystem = new File(this.uploadRootPath + File.separator +
                    "employeesPhotos" + File.separator + employee.getPhotoFileName());
        if (!photoInFilesSystem.exists()) {
            return "test/oops";
        }
        InputStream inputStream = FileUtils.openInputStream(photoInFilesSystem);
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/"+ FilenameUtils.getExtension(employee.getPhotoFileName()));
        response.setHeader("Content-Disposition","attachment; filename=\"" + employee.getPhotoFileName() +"\"");
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return "redirect:/employees/{employeeId}";
    }

    @Override
    public String toString() {
        return "EmployeeController{" +
                "firmManagerService=" + firmManagerService +
                ", userService=" + userService +
                ", employeeValidator=" + employeeValidator +
                ", uploadRootPath='" + uploadRootPath + '\'' +
                '}';
    }
}
