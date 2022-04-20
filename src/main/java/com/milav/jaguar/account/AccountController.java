package com.milav.jaguar.account;

import com.milav.jaguar.auth.errors.PasswordGenException;
import com.milav.jaguar.auth.util.AuthUtil;
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
import java.util.HashMap;

/**
 * The class deals with methods that have to do with user's accounts.
 *
 * @author Milav Shah
 * @author Jigar Shah
 */
@Controller
public class AccountController {

  /** Logger. */
  private static final Logger LOGGER = LogManager.getLogger(AccountController.class);
  /** User controller instance. */
  private final UserController userController = new UserController();
  /** Generic utilities. */
  private final JaguarUtils utils = new JaguarUtils();

  /**
   * Saves the changes the user made to their profile.
   *
   * <p>Pretty much the most complicated method. The method checks the user's password; and if it
   * matches with the one in the database, we accept any incoming account information changes.
   *
   * @param firstname the new firstname
   * @param lastname the new lastname
   * @param currentPassword the current password
   * @param newEmail the new email
   * @param newPassword the new password
   * @param model model
   * @param session HttpSession
   * @return String
   * @throws DBException since we are connecting to the database
   */
  @GetMapping("/savechanges")
  public String saveChanges(
      @RequestParam("firstname") String firstname,
      @RequestParam("lastname") String lastname,
      @RequestParam("currentPassword") String currentPassword,
      @RequestParam("email") String newEmail,
      @RequestParam("newPassword") String newPassword,
      Model model,
      @NotNull HttpSession session)
      throws DBException {

    String oldEmail = ((User) session.getAttribute("user")).getEmail();
    User oldInfo = userController.findUser(oldEmail);

    // Hashing the password.
    String salt = oldInfo.getSalt();
    String hashedPassword;

    try {
      hashedPassword = AuthUtil.createSecurePasswordGivenSalt(currentPassword, salt);
    } catch (PasswordGenException ex) {
      LOGGER.error("PasswordGenEx @ AccountController.saveChanges()", ex);
      model.addAttribute("error", "There is a problem authenticating. Please try again.");
      session.invalidate();
      return null;
    }

    LOGGER.info("Entering saveChanges method");

    if (utils.arePasswordsEqual(hashedPassword, oldInfo.getPassword())) {

      try {
        HashMap<String, String> map = AuthUtil.createSecurePasswordAndSalt(newPassword);

        String securedPassword = map.get("password");
        String newSalt = map.get("salt");

        User user =
            userController.updateUserInDB(
                oldInfo, firstname, lastname, newEmail, securedPassword, newSalt);

        session.removeAttribute("user");
        session.setAttribute("user", user);

        LOGGER.info("Profile saved");
      } catch (PasswordGenException pge) {
        LOGGER.error("PassGenEx @ AccountController.saveChanges()", pge);
        return "redirect:/error";
      }

      return "redirect:/profile";

    } else if (newEmail.isBlank()
        || newPassword.isBlank()
        || firstname.isBlank()
        || lastname.isBlank()
        || currentPassword.isBlank()) {
      LOGGER.info("User did not fill out all fields");
      model.addAttribute("error", "Please fill out all fields.");
      utils.fillUpInfoOnPage(
          model, oldInfo.getFirstName(), oldInfo.getLastName(), oldInfo.getEmail());
      return null;

    } else {
      model.addAttribute("error", "Please enter correct password.");
      utils.fillUpInfoOnPage(
          model, oldInfo.getFirstName(), oldInfo.getLastName(), oldInfo.getEmail());
      return null;
    }
  }

  /**
   * Deletes the users account.
   *
   * @param password the user's password confirmation.
   * @param model model
   * @param session the session in which the user's information is stored.
   * @return String
   * @throws DBException we are connecting to the database to delete the account.
   */
  @GetMapping("/removeaccount")
  public String deleteAccount(
      @RequestParam("password") String password, Model model, @NotNull HttpSession session)
      throws DBException {

    String email = ((User) session.getAttribute("user")).getEmail();
    User user = userController.findUser(email);

    if (user == null) {
      LOGGER.error("User is null!");
      return "/error";
    }
    if (password.isBlank()) {
      model.addAttribute("error","Please enter your password.");
      return null;
    }
    String salt = user.getSalt();
    String hashedPassword;

    try {
      hashedPassword = AuthUtil.createSecurePasswordGivenSalt(password, salt);
    } catch (PasswordGenException ex) {
      LOGGER.error("PasswordGenEx @ AccountController.deleteAccount()", ex);
      model.addAttribute("error", "There is a problem authenticating. Please try again.");
      session.invalidate();
      return null;
    }

    if (utils.arePasswordsEqual(hashedPassword, user.getPassword())) {
      userController.deleteUserFromDB(user.getEmail());
      session.invalidate();
      return "redirect:/login";
    }

    model.addAttribute("error", "Incorrect password, please try again.");
    return null;
  }

  /**
   * As expected, the method takes the user to the deleteaccount page.
   *
   * @return String
   */
  @GetMapping("/deleteaccount")
  public String goToDeleteAccount() {
    return null;
  }
}
