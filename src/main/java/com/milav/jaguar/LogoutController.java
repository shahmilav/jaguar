package com.milav.jaguar;

import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

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
        System.out.println("\n=========> User logged out.");

        session.removeAttribute("user");
        session.invalidate();
        return "redirect:/login";

    }

}
