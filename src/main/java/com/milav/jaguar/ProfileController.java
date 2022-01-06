package com.milav.jaguar;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    @Autowired
    private UserController userController;
    @Autowired
    private JaguarUtils jaguarUtils;
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
     * <h3>
     * Saves the changes the user made to their profile.</h3>
     * <p>
     * Pretty much the most complicated method.
     * </p>
     * 
     * @param firstname
     * @param lastname
     * @param currentPassword
     * @param newEmail
     * @param newPassword
     * @param model
     * @param session
     * @return
     * @throws DBException
     */
    @GetMapping("/savechanges")
    public String saveChanges(
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("email") String newEmail,
            @RequestParam("newPassword") String newPassword, Model model, HttpSession session)
            throws DBException {

        User oldInfo = (User) session.getAttribute("user");
        LOGGER.info("Entering saveChanges method");
        LOGGER.info("Name: " + firstname + " " + lastname + ", Current Password: "
                + currentPassword + ", New email: " + newEmail + ", new Password: " + newPassword);

        if (jaguarUtils.passwordCheck(currentPassword, oldInfo.getPassword())) {

            User user = userController.updateUserInDB(oldInfo, firstname, lastname, newEmail, newPassword);

            session.removeAttribute("user");
            session.setAttribute("user", user);

            LOGGER.info("Profile saved: NEW INFO ==> Name: " + user.getFirstName() + " " + user.getLastName()
                    + ", New email: "
                    + user.getEmail() + ", new Password: " + user.getPassword());

            return "redirect:/profile";

        } else if (currentPassword.isEmpty()) {
            LOGGER.info("User did not fill out current password.");
            model.addAttribute("error", "Please fill out current password.");
            model.addAttribute("firstname", oldInfo.getFirstName());
            model.addAttribute("lastname", oldInfo.getLastName());
            model.addAttribute("email", oldInfo.getEmail());
            return null;
        } else {
            model.addAttribute("error", "Please enter correct password.");
            model.addAttribute("firstname", oldInfo.getFirstName());
            model.addAttribute("lastname", oldInfo.getLastName());
            model.addAttribute("email", oldInfo.getEmail());
            return null;
        }
    }
}
