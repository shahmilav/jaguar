package com.milav.jaguar.application.app;

import com.milav.jaguar.application.selenium.SeleniumTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.milav.jaguar")
public class JaguarApplication {

    private static final Logger LOGGER = LogManager.getLogger(JaguarApplication.class);
    private static final SeleniumTest seleniumTest = new SeleniumTest();

    public static void main(String[] args) {

        LOGGER.info("Entering main method -->");
        SpringApplication.run(JaguarApplication.class, args);
        seleniumTest.runSelenium();
        LOGGER.info("App Started");
    }
}
