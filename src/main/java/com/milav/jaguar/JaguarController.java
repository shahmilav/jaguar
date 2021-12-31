package com.milav.jaguar;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class JaguarController {

    private static final Logger LOGGER = LogManager.getLogger(JaguarApplication.class);

    /**
     * The method gets any mapping for either {@code"/"}, {@code"/index"} or
     * {@code"/login"} and shows the login page.
     *
     * @param session
     * @return String
     */
    @GetMapping(value = { "/", "/index", "/login" })
    public String index(HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (session.getAttribute("user") != null) {
            LOGGER.info("\n" + user.getEmail() + " is logged in. Redirect to dashboard.");
            return "redirect:/dashboard";
        } else {
            return "login";
        }
    }

}
