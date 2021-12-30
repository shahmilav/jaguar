package com.milav.jaguar;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String fillUpProfile(
            Model model, HttpSession session) throws DBException {

        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
            model.addAttribute("email", user.getEmail());
            return null;
        } else {
            // TODO: return "redirect:/logout"
            return "redirect:/login";
        }
    }

}
