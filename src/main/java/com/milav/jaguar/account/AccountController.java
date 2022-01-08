package com.milav.jaguar.account;

import javax.servlet.http.HttpSession;

import com.milav.jaguar.application.JaguarApplication;
import com.milav.jaguar.database.DBException;
import com.milav.jaguar.user.User;
import com.milav.jaguar.user.UserController;
import com.milav.jaguar.utils.JaguarUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {

    @Autowired
    private UserController userController;
    @Autowired
    private JaguarUtils utils;
    private static Logger LOGGER = LogManager.getLogger(JaguarApplication.class);

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

        if (utils.passwordCheck(currentPassword, oldInfo.getPassword())) {

            User user = userController.updateUserInDB(oldInfo, firstname, lastname, newEmail, newPassword);

            session.removeAttribute("user");
            session.setAttribute("user", user);

            LOGGER.info("Profile saved: NEW INFO ==> Name: " + user.getFirstName() + " " + user.getLastName()
                    + ", New email: "
                    + user.getEmail() + ", new Password: " + user.getPassword());

            return "redirect:/profile";

        } else if (currentPassword.isBlank()) {
            LOGGER.info("User did not fill out current password.");
            model.addAttribute("error", "Please fill out current password.");
            utils.fillUpInfo(model, oldInfo.getFirstName(), oldInfo.getLastName(), oldInfo.getEmail());
            return null;
        } else {
            model.addAttribute("error", "Please enter correct password.");
            utils.fillUpInfo(model, oldInfo.getFirstName(), oldInfo.getLastName(), oldInfo.getEmail());
            return null;
        }
    }

    @GetMapping("/removeaccount")
    public String deleteAccount(
            @RequestParam("password") String password,
            Model model, HttpSession session) throws DBException {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            LOGGER.warn("User is null!");
            return "/error";
        }

        if (utils.passwordCheck(password, user.getPassword())) {
            userController.deleteUserFromDB(user.getEmail());
            session.invalidate();
            return "redirect:/login";
        } else if (password.isBlank()) {
            model.addAttribute("error", "Please enter your password.");
            return null;
        } else {
            model.addAttribute("error", "Incorrect password, please try again.");
            return null;

        }
    }

    @GetMapping("/deleteaccount")
    public String goToDeleteAccount() {
        return null;
    }

}
