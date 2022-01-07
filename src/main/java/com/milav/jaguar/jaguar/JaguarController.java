package com.milav.jaguar.jaguar;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JaguarController {

    private static Logger LOGGER = LogManager.getLogger(JaguarApplication.class);

    /**
     * The method gets any mapping for either {@code"/"}, {@code"/index"}, or
     * {@code"/login"} and shows the login page.
     *
     * @param session
     * @return String
     */
    @GetMapping(value = { "/", "/index", "/login" })
    public String index(HttpSession session) {
        LOGGER.info("Entering index method");

        if (session.getAttribute("user") != null)
            return "redirect:/dashboard";
        else
            return "login";
    }
}
