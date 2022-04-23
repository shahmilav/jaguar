package com.milav.jaguar.database.manager;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;

/**
 * Manages the database.
 *
 * @author Jigar Shah
 */
public class DBManager {

  /** The database. */
  private static MongoDatabase mongoDB = null;
  /** The database manager */
  private static DBManager dbManager = null;

  @Value("${mongodb.uri}")
  private static String dbURL;

  /** Initialize the db manager. */
  private DBManager() {
    System.out.println("@@@" + dbURL);
    init();
  }

  /**
   * Gets the database.
   *
   * @return MongoDatabase
   */
  public static MongoDatabase getMongoDB() {
    if (dbManager == null) {
      dbManager = new DBManager();
    }

    return mongoDB;
  }

  private static String getMongoDBURI() {
    return dbURL;
  }

  public static void main(String[] args) {
    System.out.println(DBManager.getMongoDBURI());
    System.out.println(dbURL);
  }

  private void init() {
    ConnectionString connectionString =
        new ConnectionString(
            "mongodb+srv://<username>:<password>@<hostname>/jaguar?retryWrites=true&w=majority");

    MongoClientSettings settings =
        MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
            .build();
    MongoClient mongoClient = MongoClients.create(settings);
    mongoDB = mongoClient.getDatabase("jaguar");
  }
}
