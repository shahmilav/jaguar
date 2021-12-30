package com.milav.jaguar;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class JaguarController {

    /**
     * The method gets any mapping for either "/", "/index" or "/login"
     * and shows the login page.
     *
     * @return String
     */
    @GetMapping(value = { "/", "/index", "/login" })
    public String index() {
        return "login";
    }

}
