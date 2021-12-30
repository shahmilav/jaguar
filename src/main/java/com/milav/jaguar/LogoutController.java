package com.milav.jaguar;

import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logoutUser(Model model, HttpSession session) {
        System.out.println("User is logged out");

        session.removeAttribute("user");
        session.invalidate();
        return "redirect:/login";

    }

}
