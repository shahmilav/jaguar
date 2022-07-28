package com.milav.jaguar.auth.logout;

import com.milav.jaguar.application.app.JaguarApplication;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The class serves as a controller for logout related methods.
 *
 * @author Milav Shah
 * @author Jigar Shah
 */
@Controller
public class LogoutController {

  /** Logger */
  private static final Logger LOGGER = LogManager.getLogger(JaguarApplication.class);

  /**
   * The method logs out the user.
   *
   * <p>The method invalidates the user's session and redirects to the login page.
   *
   * @param session HttpSession
   * @return String
   */
  @GetMapping("/logout")
  public String logoutUser(@NotNull HttpSession session) {
    LOGGER.info("Entering logoutUser method: user logged out");

    session.removeAttribute("user");
    session.invalidate();
    return "redirect:/login";
  }
}
