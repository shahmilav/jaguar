package com.milav.jaguar;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    @Autowired
    private UserController userController;
    private static final Logger LOGGER = LogManager.getLogger(JaguarApplication.class);

    /**
     * The method shows the register page on the mapping "/sign-up".
     * 
     * @param model
     * @return String
     */
    @GetMapping("/sign-up")
    public String signup(Model model) {
        return "register";
    }

    /**
     * <h3>The method creates an account for the user.</h3>
     * 
     * <ol>
     * <li>If the user already has an account, we display an error message and
     * return
     * null.</li>
     * <li>If the user did not fill out all fields, we display an error message
     * and return null.</li>
     * <li>Otherwise, we create an account for the user, add them to the database,
     * start
     * their session, and redierct them to the dashboard.</li>
     * </ol>
     * 
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @param model
     * @param session
     * @return String
     * @throws DBException
     */
    @PostMapping("/register")
    public String register(
            @RequestParam(name = "fname") String firstName,
            @RequestParam(name = "lname") String lastName,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            Model model, HttpSession session) throws DBException {

        User user = (User) session.getAttribute("user");
        LOGGER.info("\n Someone wants to sign up.");

        if (session.getAttribute("user") != null) {
            LOGGER.info("\n" + user.getEmail() + " did not log out, go to dashboard.");
            return "redirect:/dashboard";
        }

        if (userController.doesUserExist(email)) {
            model.addAttribute("error", "We already have an account for that email. Please login.");
            LOGGER.info("\nf" + email + " tried to sign up but account exists.");
            return null;

        } else if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            LOGGER.info("\n" + email + " left fields blank on sign up.");
            model.addAttribute("error", "Please fill out all fields.");
            return null;

        } else {
            userController.createUserInDB(firstName, lastName, email, password);
            user = userController.findUser(email);

            session.setAttribute("user", user);
            LOGGER.info(firstName + " " + lastName + " has made an account");
            return "redirect:/dashboard";

        }

    }

}
