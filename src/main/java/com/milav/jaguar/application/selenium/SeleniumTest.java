package com.milav.jaguar.application.selenium;

import com.milav.jaguar.application.app.JaguarApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTest {
    private static final Logger LOGGER = LogManager.getLogger(JaguarApplication.class);

    /**
     * The method runs the Selenium web driver on Firefox.
     */
    public void runSelenium() {
        LOGGER.info("Entering runSelenium method");
        WebDriver driver = new FirefoxDriver();
        LOGGER.info("Running selenium.");
        driver.get("http://localhost:8080");

        simpleSignIn(driver);
        makeAndDeleteAccount(driver);
        driver.quit();


    }

    /**
     * This method signs in the user and logs them out.
     *
     * @param driver WebDriver to run the method.
     */
    public void simpleSignIn(WebDriver driver) {

        WebElement emailBox = driver.findElement(By.name("email"));
        emailBox.sendKeys("me@milav.com");

        WebElement passwordBox = driver.findElement(By.name("password"));
        passwordBox.sendKeys("milav");

        WebElement loginButton = driver.findElement(By.name("login-btn"));
        loginButton.click();

        WebElement logoutLink = driver.findElement(By.id("logout"));
        logoutLink.click();

    }

    /**
     * The method makes a new account for "cooljaguar@jaguarlogin.com".
     *
     * @param driver
     */
    public void makeANewAccount(WebDriver driver) {

        WebElement signupLink = driver.findElement(By.id("signup-link"));
        signupLink.click();

        WebElement firstNameBox = driver.findElement(By.name("fname"));
        firstNameBox.sendKeys("Cool");

        WebElement lastNameBox = driver.findElement(By.name("lname"));
        lastNameBox.sendKeys("Jaguar");

        WebElement emailBox = driver.findElement(By.name("email"));
        emailBox.sendKeys("cooljaguar@jaguarlogin.com");

        WebElement passwordBox = driver.findElement(By.name("password"));
        passwordBox.sendKeys("Jaguar123");

        WebElement signupButton = driver.findElement(By.id("signup-btn"));
        signupButton.click();

    }

    public void editProfile(WebDriver driver) {

        WebElement profileLink = driver.findElement(By.id("profile"));
        profileLink.click();

        WebElement editProfileButton = driver.findElement(By.id("edit-profile"));
        editProfileButton.click();

        WebElement firstNameBox = driver.findElement(By.name("firstname"));
        firstNameBox.sendKeys(" :)");

        WebElement newPasswordBox = driver.findElement(By.name("newPassword"));
        newPasswordBox.sendKeys("JaguarABC");

        WebElement currentPasswordBox = driver.findElement(By.name("currentPassword"));
        currentPasswordBox.sendKeys("Jaguar123");

        WebElement saveChangesButton = driver.findElement(By.name("savechanges"));
        saveChangesButton.click();

    }

    public void deleteProfile(WebDriver driver) {

        WebElement deleteAccountLink = driver.findElement(By.id("del-account"));
        deleteAccountLink.click();

        WebElement passwordBox = driver.findElement(By.name("password"));
        passwordBox.sendKeys("JaguarABC");

        WebElement deleteAccountButton = driver.findElement(By.id("del-acc"));
        deleteAccountButton.click();

    }

    public void makeAndDeleteAccount(WebDriver driver) {
        makeANewAccount(driver);
        editProfile(driver);
        deleteProfile(driver);
    }
}


