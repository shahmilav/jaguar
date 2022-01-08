package com.milav.jaguar.application;

import org.apache.logging.log4j.Logger;

import com.milav.jaguar.database.DBException;

import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JaguarApplication {

    private static Logger LOGGER = LogManager.getLogger(JaguarApplication.class);

    @Autowired
    public static void main(String[] args) throws DBException {

        SpringApplication.run(JaguarApplication.class, args);
        LOGGER.info("App Started.");
    }

}
