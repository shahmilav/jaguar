package com.milav.jaguar;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    /**
     * The method gets the current user from the session. If the user is null (has
     * not signed in), we redirect them to the login page. Otherwise, we fill in
     * their information on the page.
     * 
     * @param model
     * @param session
     * @return String
     * @throws DBException
     */
    @GetMapping("/profile")
    public String fillUpProfile(
            Model model, HttpSession session) throws DBException {

        User user = (User) session.getAttribute("user");
        if (user != null) {
            String fullName = user.getFirstName() + " " + user.getLastName();
            model.addAttribute("name", fullName);
            model.addAttribute("email", user.getEmail());
            return null;
        } else {

            return "redirect:/login";
        }
    }

}
