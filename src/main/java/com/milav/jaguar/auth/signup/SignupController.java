package com.milav.jaguar.auth.signup;

import com.milav.jaguar.auth.errors.PasswordGenException;
import com.milav.jaguar.auth.util.AuthUtil;
import com.milav.jaguar.database.errors.DBException;
import com.milav.jaguar.user.User;
import com.milav.jaguar.user.UserController;
import java.util.HashMap;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The class serves as a controller for signup related methods.
 *
 * @author Milav Shah
 * @author Jigar Shah
 */
@Controller
public class SignupController {

  /** Logger. */
  private static final Logger LOGGER = LogManager.getLogger(SignupController.class);
  /** User controller. */
  private final UserController userController = new UserController();

  /**
   * The method shows the register page.
   *
   * @return String
   */
  @GetMapping("/sign-up")
  public String signup() {
    LOGGER.info("Entering signup method");
    return "register";
  }

  /**
   * The method checks the user's signup credentials and creates a user if they are valid.
   *
   * @param firstName first name
   * @param lastName last name
   * @param email email
   * @param password password
   * @param model model
   * @param session HttpSession
   * @return String
   * @throws DBException since we are connecting to the database
   */
  @PostMapping("/register")
  public String register(
      @RequestParam(name = "fname") String firstName,
      @RequestParam(name = "lname") String lastName,
      @RequestParam(name = "email") String email,
      @RequestParam(name = "password") String password,
      Model model,
      @NotNull HttpSession session)
      throws DBException {

    LOGGER.info("Entering register method");

    if (session.getAttribute("user") != null) {
      return "redirect:/dashboard";

    } else if (userController.doesUserExist(email)) {
      model.addAttribute("error", "We already have an account for that email. Please login.");
      return null;

    } else if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
      model.addAttribute("error", "Please fill out all fields.");
      return null;

    } else if (!AuthUtil.validate(email)) {
      model.addAttribute("error", "Please enter a valid email.");
      return null;

    } else {

      try {
        HashMap<String, String> map = AuthUtil.createSecurePasswordAndSalt(password);

        String securedPasswd = map.get("password");
        String salt = map.get("salt");

        userController.createUserInDB(firstName, lastName, email, securedPasswd, salt);
        User user = userController.findUser(email);

        session.setAttribute("user", user);

      } catch (PasswordGenException pge) {
        return "redirect:/error";
      }

      return "redirect:/dashboard";
    }
  }
}
