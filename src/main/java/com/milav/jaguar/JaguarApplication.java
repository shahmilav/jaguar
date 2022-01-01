package com.milav.jaguar;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class JaguarApplication {

    private static Logger LOGGER = LogManager.getLogger(JaguarApplication.class);

    @Autowired
    public static void main(String[] args) throws DBException {

        SpringApplication.run(JaguarController.class, args);
        LOGGER.warn("\nApp started.");

    }

}
