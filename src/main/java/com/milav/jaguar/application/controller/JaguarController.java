package com.milav.jaguar.application.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * General controller for basic startup pages.
 *
 * @author Milav Shah
 * @author Jigar Shah
 */
@Controller
public class JaguarController {

    /**
     * The logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(JaguarController.class);

    /**
     * The method shows the login page.
     *
     * @param session session
     * @return String
     */
    @GetMapping(value = {"/", "/login"})
    public String login(HttpSession session) {
        LOGGER.info("Entering login method");

        if (session.getAttribute("user") != null) {
            // if user is already signed in, redirect them to dashboard.
            return "redirect:/dashboard";
        } else {
            // otherwise, let them log in.
            return "login";
        }
    }

    /**
     * No one likes "/index" in their url: this avoids that.
     *
     * @return String
     */
    @GetMapping(value = {"/index"})
    public String index() {
        return "redirect:/login";
    }
}
