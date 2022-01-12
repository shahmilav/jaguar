package com.milav.jaguar.auth.signup;

import com.milav.jaguar.application.app.JaguarApplication;
import com.milav.jaguar.database.errors.DBException;
import com.milav.jaguar.user.User;
import com.milav.jaguar.user.UserController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class SignupController {

    private static final Logger LOGGER = LogManager.getLogger(JaguarApplication.class);
    private final UserController userController = new UserController();

    /**
     * <h3>The method shows the register page.</h3>
     *
     * @param model model
     * @return String
     */
    @GetMapping("/sign-up")
    public String signup(Model model) {
        LOGGER.info("Entering signup method");
        return "register";
    }

    /**
     * <h3>The method creates an account for the user.</h3>
     *
     * <ol>
     * <li>If the user already has an account, we display an error message and
     * return null.</li>
     * <li>If the user did not fill out all fields, we display an error message
     * and return null.</li>
     * <li>Otherwise, we create an account for the user, add them to the database,
     * start their session, and redirect them to the dashboard.</li>
     * </ol>
     *
     * @param firstName first name
     * @param lastName  last name
     * @param email     email
     * @param password  password
     * @param model     model
     * @param session   HttpSession
     * @return String
     * @throws DBException since we are connecting to the database
     */
    @PostMapping("/register")
    public String register(@RequestParam(name = "fname") String firstName, @RequestParam(name = "lname") String lastName, @RequestParam(name = "email") String email, @RequestParam(name = "password") String password, Model model, HttpSession session) throws DBException {

        User user = (User) session.getAttribute("user");
        LOGGER.info("Entering register method: " + email);

        if (session.getAttribute("user") != null) return "redirect:/dashboard";

        else if (userController.doesUserExist(email)) {
            model.addAttribute("error", "We already have an account for that email. Please login.");
            return null;

        } else if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            model.addAttribute("error", "Please fill out all fields.");
            return null;

        } else {
            userController.createUserInDB(firstName, lastName, email, password);
            user = userController.findUser(email);

            session.setAttribute("user", user);
            return "redirect:/dashboard";

        }
    }
}
