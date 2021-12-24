package com.example.jaguar;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Controller
public class JaguarApplication {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private final static String validUsername = "me@milav.com";
    private final static String validPassword = "milav";
    private boolean isUserLoggedIn = false;

    public static void main(String[] args) {
        SpringApplication.run(JaguarApplication.class, args);
    }

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
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
        if (isUserLoggedIn) {
            return "dashboard";
        } else {
            return "index";
        }
    }

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("title", "Login Page");
        return "index";
    }
}
