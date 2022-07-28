package com.milav.jaguar.user;

import static com.mongodb.client.model.Filters.eq;

import com.milav.jaguar.database.errors.DBException;
import com.milav.jaguar.database.manager.DBManager;
import com.milav.jaguar.user.util.UserUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

/**
 * The class is the controller that manages methods that have to do with users.
 *
 * @author Milav Shah
 * @author Jigar Shah
 */
@Service
public class UserController {

  /** Logger. */
  private static final Logger LOGGER = LogManager.getLogger(UserController.class);
  /** User related utilities. */
  private final UserUtil userUtil = new UserUtil();
  /** Generic database connection error. */
  String dbError = "Error accessing the database.";
  /** The name of the MongoDB collection in the database. */
  String collectionName = "USER_PROFILE";

  // Field Names

  /** First name field. */
  String firstNameField = "firstName";
  /** Last name field. */
  String lastNameField = "lastName";
  /** Email field. */
  String emailField = "email";
  /** Password field. */
  String passwordField = "password";
  /** Salt field. */
  String saltField = "salt";

  DBManager dbManager = new DBManager();

  /**
   * The method creates an entry in the database with the given information.
   *
   * @param firstName first name of the user.
   * @param lastName last name of the user.
   * @param email email of the user.
   * @param password user's password.
   * @param salt sprinkle the salt to make password guessing hard
   */
  public void createUserInDB(
      String firstName,
      String lastName,
      @NotNull String email,
      @NotNull String password,
      @NotNull String salt) {

    LOGGER.info("Entering createUserInDB method");
    MongoDatabase db = dbManager.getMongoDB();

    // get database collection.
    MongoCollection<Document> collection = db.getCollection(collectionName);

    // put user credentials into the document.
    Document document = new Document();
    document.put(firstNameField, firstName);
    document.put(lastNameField, lastName);
    document.put(emailField, email.toLowerCase());
    document.put(passwordField, password);
    document.put(saltField, salt);

    // place the date in the document.
    Date date = new Date();
    document.put("createDateTime", date);
    document.put("updateDateTime", date);
    document.put("lastLoginDateTime", date);

    // insert documents into the database.
    collection.insertOne(document);
    collection.createIndex(new BasicDBObject(emailField, 1));
    LOGGER.info("User created in DB");
  }

  /**
   * The method finds a user given an email. It is assumed that each user has a unique email
   * address. You cannot have multiple users with the same email address.
   *
   * @param email the search query
   * @return boolean
   */
  public boolean doesUserExist(@NotNull String email) {

    MongoDatabase db = dbManager.getMongoDB();
    Document document = new Document();
    document.put(emailField, email.toLowerCase());
    MongoCollection<Document> collection = db.getCollection(collectionName);
    return (collection.find(document).first() != null);
  }

  /**
   * The method finds a user given an email address.
   *
   * <p>It returns a User object with prefilled data retrieved from the database. The user can be
   * null if the user does not exist in the database.
   *
   * @param email the search query to match against the database.
   * @return User
   * @throws DBException we have to connect to the database to find the user.
   */
  public User findUser(@NotNull String email) throws DBException {

    MongoDatabase db;

    db = dbManager.getMongoDB();

    Document document = new Document();
    document.put(emailField, email.toLowerCase());
    MongoCollection<Document> collection = db.getCollection(collectionName);
    FindIterable<Document> findIterable = collection.find(document);
    Document result;

    try {
      result = findIterable.first();
    } catch (MongoTimeoutException timeoutException) {
      throw new DBException(dbError, timeoutException);
    }

    if (result != null) {

      LOGGER.info("User found!");
      return new User(
          email,
          result.getString(passwordField),
          result.getString(saltField),
          result.getString(firstNameField),
          result.getString(lastNameField));

    } else {
      LOGGER.info("User not found");
      return null;
    }
  }

  /**
   * The method finds a user in the database and edits the entry.
   *
   * @param oldInfo the user's old information
   * @param firstname the user's new first name
   * @param lastname the user's new last name
   * @param email the user's new email
   * @param password the user's new password
   * @return User
   */
  public User updateUserInDB(
      @NotNull User oldInfo,
      String firstname,
      String lastname,
      String email,
      String password,
      String salt) {
    MongoDatabase db;

    db = dbManager.getMongoDB();

    MongoCollection<Document> collection = db.getCollection(collectionName);

    BasicDBObject searchQuery = new BasicDBObject().append(emailField, oldInfo.getEmail());
    BasicDBObject newDocument = new BasicDBObject();

    userUtil.appendDoc("$set", newDocument, firstNameField, firstname);

    collection.updateOne(searchQuery, newDocument);
    LOGGER.info(newDocument);

    userUtil.appendDoc("$set", newDocument, lastNameField, lastname);

    collection.updateOne(searchQuery, newDocument);
    LOGGER.info(newDocument);

    userUtil.appendDoc("$set", newDocument, passwordField, password);
    collection.updateOne(searchQuery, newDocument);

    userUtil.appendDoc("$set", newDocument, saltField, salt);
    collection.updateOne(searchQuery, newDocument);

    userUtil.appendDoc("$set", newDocument, emailField, email);
    collection.updateOne(searchQuery, newDocument);

    return userUtil.fillUpUser(firstname, lastname, email, password);
  }

  /**
   * Deletes the user from the database.
   *
   * @param email the search query.
   */
  public void deleteUserFromDB(String email) {

    LOGGER.info("Entering deleteUserFromDB");
    MongoDatabase db;

    db = dbManager.getMongoDB();

    MongoCollection<Document> collection = db.getCollection(collectionName);

    Bson query = eq("email", email);
    collection.deleteOne(query);
  }
}
