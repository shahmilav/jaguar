package com.milav.jaguar.application.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SeleniumTest {
    public WebDriver driver;

    /**
     * The method runs the Selenium web driver on Firefox.
     */
    public void runSelenium() {

        FirefoxOptions options = new FirefoxOptions();
        driver = new FirefoxDriver(options);

        driver.quit();
    }
}


