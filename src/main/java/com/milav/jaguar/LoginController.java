package com.milav.jaguar;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserController userController;
    private User user;
    private boolean isUserLoggedIn = false;

    /**
     * 
     * The method decides what to do when the login form is submitted.
     * If the method returns null, the page does not change.
     * 
     * If the user does not exist, it sends an error message and returns null.
     * If any fields are blank, it sends an error message and returns null.
     * If the user enters the right password, the method redirects the user to their
     * dashboard and starts their session.
     * If the user enters the wrong password, an error message is shown and the
     * method returns null.
     * 
     * @param email
     * @param password
     * @param model
     * @param session
     * @return String
     * @throws DBException
     */
    @PostMapping("/login")
    public String authenticate(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            Model model, HttpSession session) throws DBException {

        user = userController.findUser(email);

        if (email.isBlank() || password.isBlank()) {
            model.addAttribute("error", "Please fill out all fields.");
            return null;
        }

        if (user == null) {
            model.addAttribute("error", "This account does not exist. Please sign up.");
            isUserLoggedIn = false;
            return null;
        }

        if (user.getPassword().equals(password)) {
            isUserLoggedIn = true;
            session.setAttribute("user", user);
            return "redirect:/dashboard";

        } else {
            model.addAttribute("error", "Incorrect password, please try again.");
            isUserLoggedIn = false;
            return null;
        }

    }

    /**
     * The method validates the user that is logging in. If the user is not logged
     * in, the method redirects them to the login page. Otherwise, we ensure the
     * user exists. If they exist, we show a welcome message on the
     * dashboard. If they are null, we redirect them to the login page.
     * 
     * @param model
     * @return String
     */
    @GetMapping("/dashboard")
    public String validateUser(Model model) {
        if (isUserLoggedIn) {
            if (user != null) {

                model.addAttribute("name", ("Welcome to Jaguar Dashboard, " + user.getFirstName() + "."));
                return "dashboard";
            } else {
                System.out.println("User = Null");
                return "redirect:/login";
            }
        } else {
            return "redirect:/login";
        }
    }

}
