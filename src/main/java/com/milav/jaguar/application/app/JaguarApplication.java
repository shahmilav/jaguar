package com.milav.jaguar.application.app;

import com.milav.jaguar.database.errors.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.milav.jaguar")
public class JaguarApplication {

    private static final Logger LOGGER = LogManager.getLogger(JaguarApplication.class);

    public static void main(String[] args) throws DBException {

        LOGGER.info("Entering main method -->");
        SpringApplication.run(JaguarApplication.class, args);
        LOGGER.info("App Started");
    }
}
