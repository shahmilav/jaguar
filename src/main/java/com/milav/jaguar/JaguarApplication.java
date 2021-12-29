package com.milav.jaguar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@Controller
public class JaguarApplication {

    @Autowired
    private UserController userController;
    User user = null;
    private final static String validUsername = "me@milav.com";
    private boolean isUserLoggedIn = false;

    @Autowired
    public static void main(String[] args) throws DBException {

        SpringApplication.run(JaguarApplication.class, args);
    }

    @PostMapping("/login")
    public String authenticate(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            Model model) throws DBException {

        User user = userController.findUser(email);

        if (user == null) {
            model.addAttribute("error", "This account does not exist. Please sign up.");
            return null;
        }

        if (user.getPassword().equals(password)) {
            isUserLoggedIn = true;
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Incorrect password, please try again.");
            // return "redirect:/index";
            return null;
        }
    }

    @GetMapping("/dashboard")
    public String validateUser(Model model) {
        if (isUserLoggedIn) {
            model.addAttribute("name", user.getFirstName());
            return "dashboard";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping(value = { "/", "/index", "/login" })
    public String index(Model model) {
        model.addAttribute("title", "Login Page");
        model.addAttribute("error", "");
        return "login";
    }

    @GetMapping("/sign-up")
    public String signup(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam(name = "fname") String firstName,
            @RequestParam(name = "lname") String lastName,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            Model model) throws DBException {

        if (userController.doesUserExist(email)) {
            model.addAttribute("error", "We already have an account for that email. Please login.");
            return null;
        } else {
            userController.createUserInDB(firstName, lastName, email, password);
            isUserLoggedIn = true;
            return "redirect:/dashboard";

        }

    }
}
