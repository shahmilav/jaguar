package com.milav.jaguar.application.controller;

import com.milav.jaguar.application.app.JaguarApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class JaguarController {

    private static final Logger LOGGER = LogManager.getLogger(JaguarApplication.class);

    /**
     * The method gets any mapping for either {@code"/"}, {@code"/index"}, or
     * {@code"/login"} and shows the login page.
     *
     * @param session session
     * @return String
     */
    @GetMapping(value = {"/", "/login"})
    public String login(HttpSession session) {
        LOGGER.info("Entering login method");

        if (session.getAttribute("user") != null) return "redirect:/dashboard";
        else return "login";
    }

    /**
     * No one likes "/index" in their url: this avoids that.
     */
    @GetMapping(value = {"/index"})
    public String index() {
        return "redirect:/login";
    }
}
