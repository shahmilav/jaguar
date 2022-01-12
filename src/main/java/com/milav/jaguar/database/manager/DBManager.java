package com.milav.jaguar.database.manager;

import com.milav.jaguar.application.app.JaguarApplication;
import com.milav.jaguar.database.errors.DBException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;

import java.net.UnknownHostException;

/**
 * @author jigarshah
 */
public class DBManager {

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(JaguarApplication.class);
    private static MongoDatabase mongoDB = null;
    private static DBManager dbManager = null;


    private DBManager() throws UnknownHostException {
        init();
    }

    public static MongoDatabase getMongoDB() throws DBException {
        if (dbManager == null) {
            try {
                dbManager = new DBManager();
            } catch (UnknownHostException ex) {
                LOGGER.error(ex);
                throw new DBException(ex.getMessage(), ex.fillInStackTrace());
            }
        }

        return mongoDB;
    }

    private void init() throws UnknownHostException {

        String uri = "mongodb+srv://milav:pulsar66@cluster0.jnvid.mongodb.net/jaguar?retryWrites=true&w=majority";

        MongoClient mongoClient = MongoClients.create(uri);
        mongoDB = mongoClient.getDatabase("jaguar");

    }
}
