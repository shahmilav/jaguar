package com.milav.jaguar.utils;

import org.springframework.beans.factory.annotation.Value;

public class ConfigTest {

    //   TODO: why is this value null? fix this
  /*  @Value("${mongodb.uri}")
  static String dbURI;*/

    @Value("${mongodb.database}")
    String dbName;

    public ConfigTest(@Value("${mongodb.uri}") String dbURI) {
        // this is a constructor, it should work?
    }

    public static void main(String[] args) {
        /* TODO document why this method is empty */
    }
}
