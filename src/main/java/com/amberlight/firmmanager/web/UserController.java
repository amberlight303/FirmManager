package com.amberlight.firmmanager.web;


import com.amberlight.firmmanager.model.User;
import com.amberlight.firmmanager.service.FirmManagerService;
import com.amberlight.firmmanager.service.SecurityService;
import com.amberlight.firmmanager.service.UserService;
import com.amberlight.firmmanager.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for pages related with the {@link User} entity.
 */

@Controller
public class UserController {

    @Autowired
    private FirmManagerService firmManagerService;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    /**
     * Handling GET request for getting a registration form.
     */
    @RequestMapping(value = "/admin/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    /**
     * This method will be called on a form submission, handling POST request for
     * registration a <code>User</code> in the data store. It also validates user input.
     */
    @RequestMapping(value = "/admin/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        if (userForm.isCreateEmployeeOrNotFlag()) {
            // Send the user as a redirect attribute for subsequent operations with
            // creation an employee and binding that user with a new employee
            redirectAttributes.addFlashAttribute("userForm", userForm);
            return "redirect:/admin/employees/new";
        }
        return "redirect:/admin";
    }

    /**
     * Handling GET request for getting a login page.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ( auth != null && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }
        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        return "login";
    }

    /**
     * Handling GET request for getting a start page (forward to show first page of posts).
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(Model model) {
        return "forward:/page/1";
    }

    /**
     * Handling GET request for getting a <code>User</code>'s details page.
     */
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public String showUsers(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("user", this.userService.findUserByIdFetchEmployee(userId));
        return "users/userDetails";
    }

    @Override
    public String toString() {
        return "UserController{" +
                "firmManagerService=" + firmManagerService +
                ", userService=" + userService +
                ", securityService=" + securityService +
                ", userValidator=" + userValidator +
                '}';
    }
}
