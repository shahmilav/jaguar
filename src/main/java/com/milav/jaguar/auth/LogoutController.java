package com.milav.jaguar.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpSession;

import com.milav.jaguar.jaguar.JaguarApplication;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    private static Logger LOGGER = LogManager.getLogger(JaguarApplication.class);

    /**
     * The method logs out the user. The method invalidates the user's session and
     * redirects to the login page.
     * 
     * @param model
     * @param session
     * @return String
     */
    @GetMapping("/logout")
    public String logoutUser(Model model, HttpSession session) {
        LOGGER.info("Entering logoutUser method: user logged out");

        session.removeAttribute("user");
        session.invalidate();
        return "redirect:/login";

    }

}
