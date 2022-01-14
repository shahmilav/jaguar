package com.milav.jaguar.application.selenium;

import com.milav.jaguar.application.app.JaguarApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SeleniumTest {
    private static final Logger LOGGER = LogManager.getLogger(JaguarApplication.class);


    /**
     * The method runs the Selenium web driver on Firefox.
     */
    public void runSelenium() {
        LOGGER.info("Entering runSelenium method");

        FirefoxOptions options = new FirefoxOptions();

        WebDriver driver = new FirefoxDriver(options);
        LOGGER.info("Running selenium.");
        driver.quit();
    }
}


