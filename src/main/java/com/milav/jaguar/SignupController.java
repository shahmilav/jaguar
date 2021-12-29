package com.milav.jaguar;

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
    private boolean isUserLoggedIn;

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
            isUserLoggedIn = false;
            return null;
        } else if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            model.addAttribute("error", "Please fill out all fields.");
            return null;
        } else {
            userController.createUserInDB(firstName, lastName, email, password);
            isUserLoggedIn = true;
            return "redirect:/dashboard";

        }

    }

}
