package com.milav.jaguar.auth.logout;

import com.milav.jaguar.application.app.JaguarApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    private static final Logger LOGGER = LogManager.getLogger(JaguarApplication.class);

    /**
     * The method logs out the user.
     * <p>
     * The method invalidates the user's session and redirects to the login page.
     *
     * @param model   model
     * @param session HttpSession
     * @return String
     */
    @GetMapping("/logout")
    public String logoutUser(Model model, @NotNull HttpSession session) {
        LOGGER.info("Entering logoutUser method: user logged out");

        session.removeAttribute("user");
        session.invalidate();
        return "redirect:/login";

    }

}
