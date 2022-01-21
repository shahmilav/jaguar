package com.milav.jaguar.user;

import com.milav.jaguar.application.app.JaguarApplication;
import com.milav.jaguar.database.errors.DBException;
import com.milav.jaguar.database.manager.DBManager;
import com.milav.jaguar.user.util.UserUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.mongodb.client.model.Filters.eq;

/**
 * The class is the controller that manages methods that have to do with users.
 *
 * @author Milav Shah
 * @author Jigar Shah
 */
@Service
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(JaguarApplication.class);
    private final UserUtil userUtil = new UserUtil();

    /**
     * The method creates an entry in the database with the given information.
     *
     * @param firstName first name of the user.
     * @param lastName  last name of the user.
     * @param email     email of the user.
     * @param password  user's password.
     * @throws DBException since we are dealing with the database.
     */
    public void createUserInDB(String firstName, String lastName, @NotNull String email, @NotNull String password) throws DBException {

        LOGGER.info("Entering createUserInDB method: " + email);
        MongoDatabase db = DBManager.getMongoDB();

        // get database collection.
        MongoCollection<Document> collection = db.getCollection("USER_PROFILE");

        // put user credentials into the document.
        Document document = new Document();
        document.put("firstName", firstName);
        document.put("lastName", lastName);
        document.put("email", email.toLowerCase());
        document.put("password", password);

        // place the date in the document.
        Date date = new Date();
        document.put("createDateTime", date);
        document.put("updateDateTime", date);
        document.put("lastLoginDateTime", date);

        // insert documents into the database.
        collection.insertOne(document);
        collection.createIndex(new BasicDBObject("email", 1));
        LOGGER.info("User created in DB: " + firstName + " " + lastName + ": " + email);
    }

    /**
     * The method finds a user given an email.
     * It is assumed that each user has a unique email address. You cannot have
     * multiple users with the same email
     * address.
     *
     * @param email the search query
     * @return boolean
     * @throws DBException since we are dealing with the database
     */
    public boolean doesUserExist(@NotNull String email) throws DBException {

        MongoDatabase db = DBManager.getMongoDB();
        Document document = new Document();
        document.put("email", email.toLowerCase());
        MongoCollection<Document> collection = db.getCollection("USER_PROFILE");
        return (collection.find(document).first() != null);
    }

    /**
     * The method finds a user given an email address.
     * <p>
     * It returns a User object with prefilled data retrieved
     * from the database. The user can be null if the user
     * does not exist in the database.
     *
     * @param email the search query to match against the database.
     * @return User
     * @throws DBException we have to connect to the database to find the user
     */
    public User findUser(@NotNull String email) throws DBException {

        MongoDatabase db = DBManager.getMongoDB();
        Document document = new Document();
        document.put("email", email.toLowerCase());
        MongoCollection<Document> collection = db.getCollection("USER_PROFILE");
        FindIterable<Document> findIterable = collection.find(document);

        Document result = findIterable.first();
        if (result != null) {
            LOGGER.info(email + " found in database.");
            return new User(email, result.getString("password"), result.getString("firstName"), result.getString("lastName"));
        } else {
            LOGGER.info(email + " not found in database.");
            return null;
        }
    }

    /**
     * The method finds a user in the database and edits the entry.
     *
     * @param oldInfo   the user's old information
     * @param firstname the user's new first name
     * @param lastname  the user's new last name
     * @param email     the user's new email
     * @param password  the user's new password
     * @return User
     * @throws DBException since we are dealing with the database
     */
    public User updateUserInDB(@NotNull User oldInfo, String firstname, String lastname, String email, String password) throws DBException {

        MongoDatabase db = DBManager.getMongoDB();
        MongoCollection<Document> collection = db.getCollection("USER_PROFILE");

        BasicDBObject searchQuery = new BasicDBObject().append("email", oldInfo.getEmail());
        BasicDBObject newDocument = new BasicDBObject();

        newDocument.append("$set", new BasicDBObject().append("firstName", firstname));
        collection.updateOne(searchQuery, newDocument);
        LOGGER.info(newDocument);

        newDocument.append("$set", new BasicDBObject().append("lastName", lastname));
        collection.updateOne(searchQuery, newDocument);
        LOGGER.info(newDocument);

        newDocument.append("$set", new BasicDBObject().append("password", password));
        collection.updateOne(searchQuery, newDocument);
        LOGGER.info(newDocument);

        newDocument.append("$set", new BasicDBObject().append("email", email));
        collection.updateOne(searchQuery, newDocument);
        LOGGER.info(newDocument);

        collection.updateOne(searchQuery, newDocument);

        return userUtil.fillUpUser(firstname, lastname, email, password);
    }

    /**
     * Deletes the user from the database.
     *
     * @param email the search query.
     * @throws DBException since we are dealing with the database.
     */
    public void deleteUserFromDB(String email) throws DBException {

        LOGGER.info("Entering deleteUserFromDB");
        MongoDatabase db = DBManager.getMongoDB();
        MongoCollection<Document> collection = db.getCollection("USER_PROFILE");

        Bson query = eq("email", email);
        collection.deleteOne(query);
        LOGGER.info("User deleted: " + email);
    }
}
