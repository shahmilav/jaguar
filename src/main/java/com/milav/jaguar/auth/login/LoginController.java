package com.milav.jaguar.auth.login;

import com.milav.jaguar.application.app.JaguarApplication;
import com.milav.jaguar.database.errors.DBException;
import com.milav.jaguar.user.User;
import com.milav.jaguar.user.UserController;
import com.milav.jaguar.utils.JaguarUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * The class serves as the controller for login related methods.
 *
 * @author Milav Shah
 * @author Jigar Shah
 */
@Controller
public class LoginController {

    private static final Logger LOGGER = LogManager.getLogger(JaguarApplication.class);
    private final UserController userController = new UserController();
    private final JaguarUtils utils = new JaguarUtils();


    /**
     * The method decides what to do when the login form is submitted.
     * <p>
     * <ul>
     * <li>If the method returns null, the page does not change.</li>
     * <li>If the user is already logged in, redirect them to dashboard.</li>
     * <li>If the user does not exist, it sends an error message and returns null.</li>
     * <li>If any fields are blank, it sends an error message and returns null.</li>
     * <li>If the user enters the right password, the method redirects the user to their
     * dashboard and starts their session.</li>
     * <li>If the user enters the wrong password, an error message is shown and the
     * method returns null.</li>
     * </ul>
     *
     * @param email    the email entered.
     * @param password the password entered.
     * @param model    model.
     * @param session  HttpSession.
     * @return String
     * @throws DBException since we are connecting to the database in order to authenticate user.
     */
    @PostMapping("/login")
    public String authenticate(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password, Model model, HttpSession session) throws DBException {

        User user = userController.findUser(email); // find user from email given
        LOGGER.info("Entering authenticate method: " + email);
        String errorMessage = "There is an error processing your request, please try again.";

        /* We want to check if any fields are blank before we check if
        passwords match in order to prevent a valid email or password being "".
        That is the main reason for the select ordering of the following if statements. */

        if (email.isBlank() || password.isBlank()) {

            errorMessage = "Please fill out all fields.";
            model.addAttribute("error", errorMessage);
            session.invalidate();
            return null;

        } else if (user == null) {

            errorMessage = "This account does not exist. Please sign up.";
            model.addAttribute("error", errorMessage);
            session.invalidate();
            return null;

        } else if (utils.arePasswordsEqual(user.getPassword(), password)) {

            session.setAttribute("user", user);
            return "redirect:/dashboard";

        } else if (!utils.arePasswordsEqual(user.getPassword(), password)) {

            errorMessage = "Incorrect password, please try again.";
            model.addAttribute("error", errorMessage);
            session.invalidate();
            return null;

        } else {

            LOGGER.error("Trouble processing login request --> com.milav.jaguar.auth.LoginController.authenticate");
            model.addAttribute("error", errorMessage);
            session.invalidate();
            return null;
        }
    }

    /**
     * The method validates the user that is logging in. If the user is not logged
     * in, the method redirects them to the login page. Otherwise, we ensure the
     * user exists. If they exist, we show a welcome message on the
     * dashboard. If they do not exist, we redirect them to the login page.
     *
     * @param model   model
     * @param session HttpSession
     * @return String
     */
    @GetMapping("/dashboard")
    public String validateUser(Model model, HttpSession session) {

        LOGGER.info("Entering validateUser method");

        if (session != null) {
            User user = (User) session.getAttribute("user");
            String welcomeMessage = ("Welcome to Jaguar Dashboard, " + user.getFirstName() + ".");
            model.addAttribute("name", welcomeMessage);
            return "dashboard";
        } else {
            return "redirect:/login";
        }
    }
}
