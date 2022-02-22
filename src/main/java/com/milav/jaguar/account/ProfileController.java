package com.milav.jaguar.account;

import com.milav.jaguar.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * The class serves as a controller for profile related methods.
 *
 * @author Milav Shah
 */
@Controller
public class ProfileController {

  /** The logger. */
  private static final Logger LOGGER = LogManager.getLogger(ProfileController.class);

  /**
   * The method gets the current user from the session.
   *
   * <p>If the user is null (has not signed in), we redirect them to the login page. Otherwise, we
   * fill in their information on the page.
   *
   * @param model model
   * @param session HttpSession
   * @return String
   */
  @GetMapping("/profile")
  public String fillUpProfile(Model model, @NotNull HttpSession session) {

    LOGGER.info("Entering fillUpProfile method.");
    LOGGER.info("User wants to see profile.");

    User user = (User) session.getAttribute("user");
    if (user != null) {

      String fullName = user.getFirstName() + " " + user.getLastName();
      model.addAttribute("name", fullName);
      model.addAttribute("email", user.getEmail());
      LOGGER.info(user.getEmail() + "'s profile is shown.");
      return null;
    } else {
      LOGGER.info("User is not logged in: redirect to login page.");
      return "redirect:/login";
    }
  }

  /**
   * The method takes you to the edit profile page.
   *
   * <p>If the user is not logged in, they are redirected to the login screen.
   *
   * @param model model
   * @param session HttpSession
   * @return String
   */
  @GetMapping("/editprofile")
  public String goToEditProfile(Model model, @NotNull HttpSession session) {

    LOGGER.info("Entering goToEditProfile method");
    User user = (User) session.getAttribute("user");

    if (user != null) {
      model.addAttribute("firstname", user.getFirstName());
      model.addAttribute("lastname", user.getLastName());
      model.addAttribute("email", user.getEmail());
      return null;
    } else {
      LOGGER.info("User not logged in, back to login.");
      return "redirect:/login";
    }
  }
}
