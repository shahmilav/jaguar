package com.milav.jaguar.auth.login;

import com.milav.jaguar.auth.errors.PasswordGenException;
import com.milav.jaguar.auth.util.AuthUtil;
import com.milav.jaguar.database.errors.DBException;
import com.milav.jaguar.user.User;
import com.milav.jaguar.user.UserController;
import com.milav.jaguar.utils.JaguarUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.logging.Level;

/**
 * The class serves as the controller for login related methods.
 *
 * @author Milav Shah
 * @author Jigar Shah
 */
@Controller
public class LoginController {

  /** Logger. */
  private static final Logger LOGGER = LogManager.getLogger(LoginController.class);
  /** User controller class. */
  private final UserController userController = new UserController();
  /** Generic utilities. */
  private final JaguarUtils utils = new JaguarUtils();

  /**
   * The method decides what to do when the login form is submitted.
   *
   * <ul>
   *   <li>If the method returns null, the page does not change.
   *   <li>If the user is already logged in, redirect them to dashboard.
   *   <li>If the user does not exist, it sends an error message and returns null.
   *   <li>If any fields are blank, it sends an error message and returns null.
   *   <li>If the user enters the right password, the method redirects the user to their dashboard
   *       and starts their session.
   *   <li>If the user enters the wrong password, an error message is shown and the method returns
   *       null.
   * </ul>
   *
   * @param email the email entered.
   * @param password the password entered.
   * @param model model.
   * @param session HttpSession.
   * @return String
   */
  @PostMapping("/login")
  public String authenticate(
      @RequestParam(name = "email") String email,
      @RequestParam(name = "password") String password,
      Model model,
      HttpSession session)
      throws DBException {

    User user;
    LOGGER.info("Entering authenticate method: " + email);

    /* We want to check if any fields are blank before we check if
    passwords match in order to prevent a valid email or password being "" (blank).
    That is the main reason for the select ordering of the following if statements. */
    if (email.isBlank() || password.isBlank()) {

      model.addAttribute("error", "Please fill out all fields.");
      session.invalidate();
      return null;
    }

    try {
      user = userController.findUser(email);
    } catch (DBException e) {
      model.addAttribute("error", "There is an error processing your request, please try again.");
      e.printStackTrace();
      return null;
    }

    if (user == null) {

      model.addAttribute("error", "This account does not exist. Please sign up.");
      session.invalidate();
      return null;
    }

    String salt = user.getSalt();
    String hashedPassword;

    try {
      hashedPassword = AuthUtil.createSecurePasswordGivenSalt(password, salt);
    } catch (PasswordGenException ex) {
      java.util.logging.Logger.getLogger(LoginController.class.getName())
          .log(Level.SEVERE, null, ex);
      model.addAttribute("error", "There is a problem authenticating. Please try again.");
      session.invalidate();
      return null;
    }

    if (utils.arePasswordsEqual(user.getPassword(), hashedPassword)) {

      session.setAttribute("user", user);
      return "redirect:/dashboard";

    } else {

      model.addAttribute("error", "Incorrect password, please try again.");
      session.invalidate();
      return null;
    }
  }

  /**
   * The method validates the user that is logging in.
   *
   * <p>If the user is not logged in, the method redirects them to the login page. Otherwise, we
   * ensure the user exists. If they exist, we show a welcome message on the dashboard. If they do
   * not exist, we redirect them to the login page.
   *
   * @param model model
   * @param session HttpSession
   * @return String
   */
  @GetMapping("/dashboard")
  public String validateUser(Model model, HttpSession session) {

    LOGGER.info("Entering validateUser method");

    if (session != null) {
      User user = (User) session.getAttribute("user");
      model.addAttribute("greeting", "How are you doing today, " + user.getFirstName() + "?");
      return "dashboard";
    } else {
      return "redirect:/login";
    }
  }
}
