package com.milav.jaguar.database.manager;

import com.milav.jaguar.database.errors.DBException;
import com.mongodb.ConnectionString;
import com.mongodb.DBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Manages the database.
 *
 * @author Jigar Shah
 */
public class DBManager {

    /**
     * The logger.
     */
    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(DBManager.class);
    /**
     * The database.
     */
    private static MongoDatabase mongoDB = null;
    /**
     * The database manager
     */
    private static DBManager dbManager = null;

    /**
     * Initialize the db manager.
     *
     * @throws UnknownHostException if we cannot determine the IP address of the
     * host.
     */
    private DBManager() throws UnknownHostException {
        init2();
    }

    /**
     * Gets the database.
     *
     * @return MongoDatabase
     * @throws DBException since we are dealing with the database.
     */
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

    private void init() {

        try ( MongoClient mongoClient
                = MongoClients.create(
                        "mongodb+srv://milav:pulsar66@cluster0.jnvid.mongodb.net/jaguar?retryWrites=true&w=majority")) {
            mongoDB = mongoClient.getDatabase("jaguar");
        }
    }

    private void init2() {

        ConnectionString connectionString = new ConnectionString("mongodb+srv://milav:pulsar66@cluster0.jnvid.mongodb.net/jaguar?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        mongoDB = mongoClient.getDatabase("jaguar");
    }

    public static void main(String[] args) throws DBException {
        MongoDatabase db = DBManager.getMongoDB();
        MongoCollection coll = db.getCollection("USER_PROFILE");

        System.out.println("The number documents in the USER_PROFILE collection: " + coll.countDocuments());
    }
}
