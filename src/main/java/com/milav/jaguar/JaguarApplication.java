package com.milav.jaguar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
public class JaguarApplication {

    @Autowired
    public static void main(String[] args) throws DBException {

        SpringApplication.run(JaguarController.class, args);
    }

}
