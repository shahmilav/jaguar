package com.example.jaguar;

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

    public static void main(String[] args) {
        SpringApplication.run(JaguarApplication.class, args);
    }

    @PostMapping("/dashboard")
    public String authenticate(@RequestParam(name = "email") String username,
            @RequestParam(name = "password") String password, Model model) {
        System.out.println(username);
        System.out.println(password);

        if ((username.equalsIgnoreCase(validUsername)) && (password.equals(validPassword))) {
            isUserLoggedIn = true;
            model.addAttribute("name", "Welcome to Jaguar Dashboard, " + username);
            return "dashboard";
        } else {
            return "index";
        }
    }

    @GetMapping("/dashboard")
    public String validateUser(@RequestParam(name = "email") String username,
            @RequestParam(name = "password") String password, Model model) {
        if (isUserLoggedIn)
            return "dashboard";
        else
            return "index";
    }

    @GetMapping(value = { "/", "/index" })
    public String index(Model model) {
        model.addAttribute("title", "Login Page");
        return "index";
    }
}