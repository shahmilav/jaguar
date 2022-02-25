package com.milav.jaguar.database.manager;

import com.milav.jaguar.database.errors.DBException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;

import java.net.UnknownHostException;

/**
 * Manages the database.
 *
 * @author Jigar Shah
 */
public class DBManager {

  /** The logger. */
  private static final org.apache.logging.log4j.Logger LOGGER =
      LogManager.getLogger(DBManager.class);
  /** The database. */
  private static MongoDatabase mongoDB = null;
  /** The database manager */
  private static DBManager dbManager = null;

  /**
   * Initialize the db manager.
   *
   * @throws UnknownHostException if we cannot determine the IP address of the host.
   */
  private DBManager() throws UnknownHostException {
    init();
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

    try (MongoClient mongoClient =
        MongoClients.create(
            "mongodb+srv://milav:pulsar66@cluster0.jnvid.mongodb.net/jaguar?retryWrites=true&w=majority")) {
      mongoDB = mongoClient.getDatabase("jaguar");
    }
  }
}
