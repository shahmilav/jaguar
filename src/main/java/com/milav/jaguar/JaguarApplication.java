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

    // @Autowired
    // private CreateUser createUser;
    private final static String validUsername = "me@milav.com";
    private final static String validPassword = "milav";
    private boolean isUserLoggedIn = false;

    @Autowired
    public static void main(String[] args) throws DBException {

        SpringApplication.run(JaguarApplication.class, args);
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam(name = "email") String username,
            @RequestParam(name = "password") String password, Model model) {
        System.out.println(username);
        System.out.println(password);

        if ((username.equalsIgnoreCase(validUsername)) && (password.equals(validPassword))) {
            isUserLoggedIn = true;
            return "redirect:/dashboard";
        } else {
            return "index";
        }
    }

    @GetMapping("/dashboard")
    public String validateUser(Model model) {
        if (isUserLoggedIn) {
            model.addAttribute("name", "Welcome to Jaguar Dashboard, " + validUsername);
            return "dashboard";
        } else {
            return "redirect:/index";
        }
    }

    @GetMapping(value = { "/", "/index" })
    public String index(Model model) {
        model.addAttribute("title", "Login Page");
        return "index";
    }

    @PostMapping("/register")
    public String signUp(
            @RequestParam(name = "fname") String firstName,
            @RequestParam(name = "lname") String lastName,
            @RequestParam(name = "email") String username,
            @RequestParam(name = "password") String password,
            Model model) {

        System.out.println(firstName);
        System.out.println(lastName);
        System.out.println(username);
        System.out.println(password);

        return "redirect:/login";

    }
}
