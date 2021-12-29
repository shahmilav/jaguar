package com.milav.jaguar;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserController userController;
    private User user = null;
    private boolean isUserLoggedIn = false;

    @PostMapping("/login")
    public String authenticate(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            Model model) throws DBException {

        user = userController.findUser(email);

        if (user == null) {
            model.addAttribute("error", "This account does not exist. Please sign up.");
            isUserLoggedIn = false;
            return null;
        }

        if (email.isBlank() || password.isBlank()) {
            model.addAttribute("error", "Please fill out all fields."); 
            return null;
        }

        if (user.getPassword().equals(password)) {
            isUserLoggedIn = true;
            return "redirect:/dashboard";

        } else {
            model.addAttribute("error", "Incorrect password, please try again.");
            isUserLoggedIn = false;
            return null;
        }
    }

    @GetMapping("/dashboard")
    public String validateUser(Model model) {
        if (isUserLoggedIn) {
            if (user != null) {

                model.addAttribute("name", user.getFirstName());
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
