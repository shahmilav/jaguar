package com.milav.jaguar;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private static Logger LOGGER = LogManager.getLogger(JaguarApplication.class);

    /**
     * <h3>The method gets the current user from the session.</h3>
     * <p>
     * If the user is null (has not signed in), we redirect them to the login page.
     * Otherwise, we fill in their information on the page.
     * </p>
     * 
     * @param model
     * @param session
     * @return String
     * @throws DBException
     */
    @GetMapping("/profile")
    public String fillUpProfile(
            Model model, HttpSession session) throws DBException {

        LOGGER.info("Entering fillUpProfile method");
        LOGGER.info("User wants to see profile.");
        User user = (User) session.getAttribute("user");
        if (user != null) {

            String fullName = user.getFirstName() + " " + user.getLastName();
            model.addAttribute("name", fullName);
            model.addAttribute("email", user.getEmail());
            LOGGER.info(user.getEmail() + "'s profile is shown");
            return null;

        } else {
            LOGGER.info("User is not logged in -> redirect to login.");
            return "redirect:/login";
        }
    }

    /**
     * <p>
     * Takes you to the edit profile page.
     * </p>
     * 
     * @param model
     * @param session
     * @return
     * @throws DBException
     */
    @GetMapping("/editprofile")
    public String goToEditProfile(
            Model model, HttpSession session) throws DBException {

        LOGGER.info("Entering goToEditProfile method");
        User user = (User) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("firstname", user.getFirstName());
            model.addAttribute("lastname", user.getLastName());
            model.addAttribute("email", user.getEmail());
            return null;
        } else {
            LOGGER.info("\nUser not logged in, back to login.");
            return "redirect:/login";
        }
    }

    /**
     * <p>
     * The method saves changes entered in the edit profile page.
     * </p>
     * 
     * @param model
     * @param session
     * @return
     * @throws DBException
     */
    @GetMapping("/savechanges")
    public String saveChanges(
            Model model, HttpSession session) throws DBException {
        LOGGER.info("Entering saveChanges method");
        /**
         * TODO:
         * Have user save changes, update changes in db.
         * Also confirm password if email and/or password are changed.
         */

        // LOGGER.info("Profile saved.");
        return "redirect:/profile";
    }
}
