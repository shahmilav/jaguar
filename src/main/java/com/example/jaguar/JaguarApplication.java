package com.example.jaguar;

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

    private final static String validUsername = "me@milav.com";
    private final static String validPassword = "milav";
    private boolean isUserLoggedIn = false;
    @Autowired
    private static UserRepository repository;

    public static void main(String[] args) {
        createUser();
        SpringApplication.run(JaguarApplication.class, args);
    }

    private static void createUser() {
        repository.save(new User("me@milav.com", "my-secret-password"));
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

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("title", "Login Page");
        return "index";
    }
}
