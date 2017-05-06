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
 *
 * @author Oleh Koryachenko
 * @version 1.0
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
    public String initFindEmployeesForm(Model model){
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
        //initiation of a search form for employees
        model.addAttribute("employee", new Employee());
        //get a list of all employees with actual data (age and exp)
        List<Employee> employees = this.firmManagerService.findAllEmployees();
        Collections.sort(employees);
        model.addAttribute("selections", employees);
        return "employees/employeesList";
    }

    /**
     * Handling GET request for searching <code>Employee</code>s
     * by last name and working position passed as a parameters.
     */
    @RequestMapping(value = "/employees/find", method = RequestMethod.GET)
    public String processFindEmployeesForm(Employee employee/*, BindingResult result*/, Map<String, Object> model) {

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
        //get a last name from search form to do search
        if (employee.getLastName() == null) {
            //empty string signifies broadest possible search
            employee.setLastName("");
        }
        //get a working position from search form to do search
        if (employee.getWorkingPosition().getName() == null) {
            //empty string signifies broadest possible search
            employee.getWorkingPosition().setName("");
        }
        //get collection of matching search criteria employees
        Collection<Employee> result = this.firmManagerService
                .findEmployeeByLastNameAndWorkingPosition(
                        employee.getLastName(),
                        employee.getWorkingPosition().getName()
                );
        model.put("selections", result);
        return "employees/employeesList";
    }

    /**
     * Handling GET request for a particular <code>Employee</code>.
     */
    @RequestMapping(value = "/employees/{employeeId}", method = RequestMethod.GET)
    public String showEmployee(@PathVariable("employeeId") int employeeId, Model model, HttpServletRequest request) {
        //get an employee to show in the page
        Employee employee = this.firmManagerService.findEmployeeByIdFetchProjectsAndUser(employeeId);
        if(employee == null){
            return "test/oops";
        }
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
            employee = this.firmManagerService.findEmployeeByIdFetchProjectsAndUser(employeeId);
        }

        //if employee has no photo, send in model default noPhoto image for displaying
        //otherwise send an employee's photo's file name
        if(employee.getPhotoFileName()==null){
            model.addAttribute("emptyPhotoName", "noPhoto.png");
        } else {
            model.addAttribute("photoName", "employeesPhotos"
                    + File.separator
                    + employee.getPhotoFileName());
        }
        model.addAttribute("employee", employee);
        return "employees/employeeDetails";
    }

    /**
     * Handling GET request for getting edit form for a particular <code>Employee</code>.
     */
    @RequestMapping(value = "/admin/employees/{employeeId}/edit", method = RequestMethod.GET)
    public String initUpdateEmployeeForm(@PathVariable("employeeId") int employeeId,
                                         ModelMap model) {
        //get an employee to show in the page
        Employee employee = this.firmManagerService.findEmployeeById(employeeId);
        if(employee == null){
            return "test/oops";
        }
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
        if(employee.getPhotoFileName()==null){
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
        //validate an inputted employee
        employeeValidator.validate(employee, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("employee", employee);
            if(employee.getPhotoFileName()==null){
                model.addAttribute("emptyPhotoName", "noPhoto.png");
            } else {
                model.addAttribute("photoName", "employeesPhotos"
                        + File.separator
                        + employee.getPhotoFileName());
            }
            return "employees/createOrUpdateEmployee";
        }
        //gender and working position options was sent to the edit form as hmtl select options
        // and employee's gender and working position fields was set only with id's of particular gender
        // and working position object
        //for correct persistence all fields should be set, so below code does fetching full Gender and
        // WorkingPosition objects from DB and properly setting an employee's respective fields
        int genderId = employee.getGender().getId();
        int workingPositionId = employee.getWorkingPosition().getId();
        Gender gender = this.firmManagerService.findGenderById(genderId);
        WorkingPosition workingPosition = this.firmManagerService.findWorkingPositionById(workingPositionId);
        employee.getWorkingPosition().setName(workingPosition.getName());
        employee.getGender().setName(gender.getName());
        Set<Project> projectSet = new HashSet<>();
        //add to the submitted Employee all projects by fetching them from the data store
        projectSet.addAll(this.firmManagerService.findEmployeeByIdFetchProjectsAndUser(employeeId).getProjects());
        employee.setProjects(projectSet);
        //if was uploaded an Employee's photo
        if (!employee.getImage().isEmpty()) {
            try {
                //convert MultipartFile into bytes
                byte[] bytes = employee.getImage().getBytes();

                //Creating rootpath string to store file
                // by getting path from the context parameter from web.xml
                String rootPath = this.uploadRootPath
                        + File.separator
                        + "employeesPhotos";
                //delete the old employee's photo if it is
                if(employee.getOldEmplPhotoName()!=null){
                    File oldPostPhoto = new File(rootPath + File.separator + employee.getOldEmplPhotoName());
                    oldPostPhoto.delete();
                }
                //Creating root directory to store file
                //make directories if there is no such directories
                File directory = new File(rootPath);
                if (!directory.exists())
                    directory.mkdirs();
                //random string creator for making new random photo file name
                RandomStringCreator randomStringCreator = new RandomStringCreator();
                //new server file
                File newServerFile;
                //file name without extension
                String fileName;
                //file name with extension
                String fullFileName;
                //files analyzer for inspection saving directory for
                // an existence of duplicates of the just generated photo's file name
                FilesAnalyzer filesAnalyzer = new FilesAnalyzer();
                //create a new File until its name will be original in the directory
                do{
                    fileName = randomStringCreator.getRandomString(7);
                    fullFileName = fileName+ "." + FilenameUtils.getExtension(employee.getImage().getOriginalFilename());
                    // Create the file on server
                    newServerFile = new File(directory.getAbsolutePath()
                            + File.separator + fullFileName);
                } while (filesAnalyzer.doDirectoryHaveFile(
                        fullFileName,
                        directory));
                //get output stream for writing bytes of the image
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(newServerFile));
                //write bytes
                stream.write(bytes);
                stream.close();
                //set an employee's photo filename with name of a just created file
                employee.setPhotoFileName(fullFileName);
            } catch (Exception e) {
                e.printStackTrace();
                return "test/oops";
            }
        } else {
            //if new photo image has not been uploaded, set employee's photo file name with old value
            employee.setPhotoFileName(this.firmManagerService.findEmployeeById(employeeId).getPhotoFileName());
        }
        //get rid of MultipartFile in the memory
        employee.setImage(null);
        EmployeeUpdater employeeUpdater = new EmployeeUpdater();
        //calculate an set actual values of age and experience fields based on birth and hire dates
        employee = employeeUpdater.calcAgeAndExpAndSetEmployee(employee, new TimeAnalyzer());
        //save the edited Employee
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
    public String addEmployee(Model model,
                              //passed as a redirect flash attribute
                              @ModelAttribute("userForm") User userForm){
        //if registration is processing and userForm
        // has passed from the previous registration form
        if(userForm.getUsername() != null) {
            //initiate employee's creation form
            Employee employee = new Employee();
            //set first name of a new employee with first name
            // of a user that would be attached with this new employee after a submitting the form
            employee.setFirstName(userForm.getFirstName());
            //set last name of a new employee with last name of the user
            employee.setLastName(userForm.getLastName());
            //set employee's userId field to attach the user with this employee
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
                                     Model model){
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
                //convert MultipartFile into bytes
                byte[] bytes = employee.getImage().getBytes();

                //Create root path string to store file
                // by getting path from the context parameter from web.xml
                String rootPath = this.uploadRootPath
                        + File.separator
                        + "employeesPhotos";
                //delete the old employee's photo if it is
                if(employee.getOldEmplPhotoName()!=null){
                    File oldPostPhoto = new File(rootPath + File.separator + employee.getOldEmplPhotoName());
                    oldPostPhoto.delete();
                }
                //Creating root directory to store file
                //make directories if there is no such directories
                File directory = new File(rootPath);
                if (!directory.exists())
                    directory.mkdirs();
                //random string creator for making new random photo file name
                RandomStringCreator randomStringCreator = new RandomStringCreator();
                //new server file
                File newServerFile;
                //file name without extension
                String fileName;
                //file name with extension
                String fullFileName;
                //files analyzer for inspection saving directory for
                // an existence of duplicates of the just generated photo's file name
                FilesAnalyzer filesAnalyzer = new FilesAnalyzer();
                //create a new File until its name will be original in the directory
                do{
                    fileName = randomStringCreator.getRandomString(7);
                    fullFileName = fileName+ "." + FilenameUtils.getExtension(employee.getImage().getOriginalFilename());
                    // Create the file on server
                    newServerFile = new File(directory.getAbsolutePath()
                            + File.separator + fullFileName);
                } while (filesAnalyzer.doDirectoryHaveFile(
                        fullFileName,
                        directory));
                //get output stream for writing bytes of the image
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(newServerFile));
                //write bytes
                stream.write(bytes);
                stream.close();
                //set an employee's photo filename with name of a just created file
                employee.setPhotoFileName(fullFileName);
                //get rid of the MultipartFile (image)
                employee.setImage(null);
                //calculate and set actual values of age and experience
                // fields based on birth and hire dates
                employee = new EmployeeUpdater().calcAgeAndExpAndSetEmployee(employee, new TimeAnalyzer());
                this.firmManagerService.saveEmployee(employee);
                //if registration is processing and an employee
                // has an id of user to bind with each other
                if (employee.getUserIdToAttachWithEmpl() != 0){
                    //attach the User to the Employee
                    this.userService.attachUserToEmployee(employee.getUserIdToAttachWithEmpl(), employee.getId());
                }
                return "redirect:/employees/" + employee.getId();
            } catch (Exception e) {
                e.printStackTrace();
                return "test/oops";
            }
        }
        //calculate and set actual values of age and experience
        // fields based on birth and hire dates
        employee = new EmployeeUpdater().calcAgeAndExpAndSetEmployee(employee, new TimeAnalyzer());
        this.firmManagerService.saveEmployee(employee);
        //if registration is processing and an employee
        // has an id of user to bind with each other
        if (employee.getUserIdToAttachWithEmpl() != 0){
            //attach the User to the Employee
            this.userService.attachUserToEmployee(employee.getUserIdToAttachWithEmpl(), employee.getId());
        }
        return "redirect:/employees/" + employee.getId();
    }

    /**
     * This method will be called on form submission, handling POST request for
     * deleting an <code>Employee</code> from the data store.
     */
    @RequestMapping(value = "/admin/employees/{employeeId}/delete", method = RequestMethod.POST)
    public String deleteEmployee(@PathVariable("employeeId") int employeeId){
        Employee employee = this.firmManagerService.findEmployeeByIdFetchUser(employeeId);
        if(employee == null) return "test/oops";
        //if an employee has photo, delete it
        if(employee.getPhotoFileName()!=null){
            File photoLocation = new File(this.uploadRootPath
                    + File.separator + "employeesPhotos" + File.separator + employee.getPhotoFileName());
            photoLocation.delete();
        }
        if(employee.getUser() != null) {
            this.firmManagerService.deleteCommentsByUserId(employee.getUser().getId());
        }
        //if an employee is bound with some user, delete the user
        if(employee.getUser()!=null){
            this.firmManagerService.deleteUserByUserId(employee.getUser().getId());
        }
        //delete an employee
        this.firmManagerService.removeEmployee(employeeId);

        return "redirect:/employees";
    }

    /**
     * Handling POST request for deleting relation
     * between certain {@link Employee} and {@link Project}.
     */
    @RequestMapping(value="/admin/employees/{employeeId}/projects/{projectId}/detach", method = RequestMethod.POST)
    public String processDetachProject(@PathVariable("projectId") int projectId,
                                        @PathVariable("employeeId") int employeeId){

        this.firmManagerService.detachProjectFromEmployee(projectId, employeeId);
        return "redirect:/employees/{employeeId}";
    }

    /**
     * Handling POST request for creation relation
     * between certain {@link Employee} and {@link Project}.
     */
    @RequestMapping(value="/admin/employees/{employeeId}/projects/{projectId}/attach", method = RequestMethod.POST)
    public String processAttachProject(@PathVariable("projectId") int projectId,
                                        @PathVariable("employeeId") int employeeId){

        this.firmManagerService.attachProjectToEmployee(projectId, employeeId);
        return "redirect:/admin/employees/{employeeId}/projects/attach";
    }

    /**
     * Handling GET request for getting page with list of projects unrelated with certain
     * <code>Employee</code> to attach <code>Project</code>s to that <code>Employee</code>.
     */
    @RequestMapping(value="/admin/employees/{employeeId}/projects/attach", method = RequestMethod.GET)
    public String initAttachProject(@PathVariable("employeeId") int employeeId, Model model){
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
        if(employee.getPhotoFileName() == null) return "test/oops";
            File photoInFilesSystem = new File(this.uploadRootPath + File.separator +
                    "employeesPhotos" + File.separator + employee.getPhotoFileName());
        if(!photoInFilesSystem.exists()) {
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
}
