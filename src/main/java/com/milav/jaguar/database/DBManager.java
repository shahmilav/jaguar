package com.milav.jaguar.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jigarshah
 */
public class DBManager {

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
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
                throw new DBException(ex.getMessage(),
                        ex.fillInStackTrace());
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
