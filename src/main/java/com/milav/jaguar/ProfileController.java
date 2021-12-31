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

        System.out.println("\n=========> User wants to see profile. -->");
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String fullName = user.getFirstName() + " " + user.getLastName();
            model.addAttribute("name", fullName);
            model.addAttribute("email", user.getEmail());
            System.out.println("\n=========> Profile shown.");
            return null;

        } else {

            System.out.println("\n=========> User not logged in, back to login.");
            return "redirect:/login";
        }
    }

    @GetMapping("/editprofile")
    public String goToEditProfile(
            Model model, HttpSession session) throws DBException {

        System.out.println("\n=========> User wants to edit profile. -->");
        User user = (User) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("firstname", user.getFirstName());
            model.addAttribute("lastname", user.getLastName());
            model.addAttribute("email", user.getEmail());
            System.out.println("\n=========> Profile saved");

            return null;
        } else {

            System.out.println("\n=========> User not logged in, back to login.");
            return "redirect:/login";
        }
    }

    @GetMapping("/savechanges")
    public String saveChanges(
            Model model, HttpSession session) throws DBException {
        System.out.println("\n=========> User wants to save changes.");
        return "redirect:/profile";

    }
}
