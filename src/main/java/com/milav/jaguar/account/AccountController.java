package com.milav.jaguar.account;

import com.milav.jaguar.application.app.JaguarApplication;
import com.milav.jaguar.database.errors.DBException;
import com.milav.jaguar.user.User;
import com.milav.jaguar.user.UserController;
import com.milav.jaguar.utils.JaguarUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AccountController {

    private static final Logger LOGGER = LogManager.getLogger(JaguarApplication.class);
    private final UserController userController = new UserController();
    private final JaguarUtils utils = new JaguarUtils();

    /**
     * <h3>
     * Saves the changes the user made to their profile.</h3>
     * <p>
     * Pretty much the most complicated method.
     * </p>
     *
     * @param firstname       the new firstname
     * @param lastname        the new lastname
     * @param currentPassword the current password
     * @param newEmail        the new email
     * @param newPassword     the new password
     * @param model           mode
     * @param session         HttpSession
     * @return String
     * @throws DBException since we are connecting to the database
     */
    @GetMapping("/savechanges")
    public String saveChanges(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, @RequestParam("currentPassword") String currentPassword, @RequestParam("email") String newEmail, @RequestParam("newPassword") String newPassword, Model model, @NotNull HttpSession session) throws DBException {

        User oldInfo = (User) session.getAttribute("user");
        LOGGER.info("Entering saveChanges method: " + oldInfo.getEmail());
        LOGGER.info("Name: " + firstname + " " + lastname + ", Current Password: " + currentPassword + ", New email: " + newEmail + ", new Password: " + newPassword);

        if (utils.passwordCheck(currentPassword, oldInfo.getPassword())) {

            User user = userController.updateUserInDB(oldInfo, firstname, lastname, newEmail, newPassword);

            session.removeAttribute("user");
            session.setAttribute("user", user);

            LOGGER.info("Profile saved: NEW INFO ==> Name: " + user.getFirstName() + " " + user.getLastName() + ", New email: " + user.getEmail() + ", new Password: " + user.getPassword());

            return "redirect:/profile";

        } else if (newEmail.isBlank() || newPassword.isBlank() || firstname.isBlank() || lastname.isBlank() || currentPassword.isBlank()) {
            LOGGER.info("User did not fill out all fields");
            model.addAttribute("error", "Please fill out all fields.");
            utils.fillUpInfoOnPage(model, oldInfo.getFirstName(), oldInfo.getLastName(), oldInfo.getEmail());
            return null;

        } else {
            model.addAttribute("error", "Please enter correct password.");
            utils.fillUpInfoOnPage(model, oldInfo.getFirstName(), oldInfo.getLastName(), oldInfo.getEmail());
            return null;
        }
    }

    @GetMapping("/removeaccount")
    public String deleteAccount(@RequestParam("password") String password, Model model, @NotNull HttpSession session) throws DBException {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            LOGGER.error("User is null!");
            return "/error";
        }

        if (utils.passwordCheck(password, user.getPassword())) {
            userController.deleteUserFromDB(user.getEmail());
            session.invalidate();
            return "redirect:/login";

        } else if (password.isBlank()) {
            model.addAttribute("error", "Please enter your password.");
            return null;

        } else if (!utils.passwordCheck(password, user.getPassword())) {
            model.addAttribute("error", "Incorrect password, please try again.");
            return null;

        } else {
            LOGGER.error("deleteAccount: something went wrong.");
            model.addAttribute("error", "Something went very wrong. Please try again.");
            return null;
        }
    }

    @GetMapping("/deleteaccount")
    public String goToDeleteAccount() {
        return null;
    }

}
