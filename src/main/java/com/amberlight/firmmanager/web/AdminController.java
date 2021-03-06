package com.amberlight.firmmanager.web;

import com.amberlight.firmmanager.model.Employee;
import com.amberlight.firmmanager.model.User;
import com.amberlight.firmmanager.service.FirmManagerService;
import com.amberlight.firmmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

/**
 * A controller for a administrator panel.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private FirmManagerService firmManagerService;

    @Autowired
    private UserService userService;

    /**
     * Handling GET request for users.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showUsers(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("users", this.userService.findAll());
        return "users/usersList";
    }

    /**
     * Handling GET request for getting a users attaching page
     * @param userId user id
     * @param model  model
     */
    @RequestMapping(value = "/users/{userId}/attach", method = RequestMethod.GET)
    public String showAttachUserToEmployee(@PathVariable("userId") int userId, Model model) {
        model.addAttribute("user", this.userService.findUserById(userId));
        model.addAttribute("employee", new Employee());
        model.addAttribute("employees", this.firmManagerService.findAllEmployees());
        return "users/attachUserToEmployee";
    }

    /**
     * Attaches a user to an employee
     * @param userId user id
     * @param employeeId employee id
     */
    @RequestMapping(value = "/users/{userId}/attach/{employeeId}", method = RequestMethod.POST)
    public String processAttachUserToEmployee(@PathVariable("userId") int userId,
                                              @PathVariable ("employeeId") int employeeId) {
        this.userService.attachUserToEmployee(userId, employeeId);
        return "redirect:/users/{userId}";
    }

    /**
     * Searches employees to attach to user
     * @param userId user Id
     * @param employee employee
     * @param model model
     */
    @RequestMapping(value = "/users/{userId}/attach/find", method = RequestMethod.GET)
    public String searchEmployeesToAttachToUser(@PathVariable("userId") int userId, @ModelAttribute Employee employee, Model model) {
        model.addAttribute("employees",
                this.firmManagerService.findEmployeeByLastNameAndWorkingPosition(
                        employee.getLastName(), ""));
        model.addAttribute("userId", userId);
        return "users/attachUserToEmployee";
    }

    /**
     * Handling POST request for deleting user.
     */
    @RequestMapping(value = "/users/{userId}/delete", method = RequestMethod.POST)
    public String processDeleteUser(@PathVariable("userId") int userId) {
        this.firmManagerService.deleteUserByUserId(userId);
        return "redirect:/admin";
    }

    /**
     * Handling GET request for searching users by last name and username passed as a parameters.
     */
    @RequestMapping(value = "/users/find", method = RequestMethod.GET)
    public String processFindUsers(User user/*, BindingResult result*/, Map<String, Object> model) {
        if (user.getLastName() == null) {
            user.setLastName("");
        }
        if (user.getUsername() == null) {
            user.setUsername("");
        }
        Collection<User> users = this.firmManagerService
                .findUsersByLastNameAndUserName(user.getLastName(), user.getUsername());
        model.put("users", users);
        return "users/usersList";
    }

    @Override
    public String toString() {
        return "AdminController{" +
                "firmManagerService=" + firmManagerService +
                ", userService=" + userService +
                '}';
    }
}
