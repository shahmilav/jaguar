package com.milav.jaguar;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class JaguarController {

    @GetMapping(value = { "/", "/index", "/login" })
    public String index(Model model) {
        model.addAttribute("title", "Login Page");
        model.addAttribute("error", "");
        return "login";
    }

}
