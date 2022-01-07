package com.milav.jaguar.user;

import java.util.Date;

import static com.mongodb.client.model.Filters.eq;

import com.milav.jaguar.database.DBException;
import com.milav.jaguar.database.DBManager;
import com.milav.jaguar.jaguar.JaguarApplication;
import com.milav.jaguar.utils.JaguarUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Service
public class UserController {

    private static Logger LOGGER = LogManager.getLogger(JaguarApplication.class);
    @Autowired
    private JaguarUtils jaguarUtils;

    /**
     * <p>
     * The method creates an entry in the database with the given
     * information.
     * </p>
     * 
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @return void
     * @throws DBException
     */
    public void createUserInDB(String firstName, String lastName, String email, String password)
            throws DBException {

        LOGGER.info("Entering createUserInDB method: " + email);
        MongoDatabase db = DBManager.getMongoDB();

        if (db.getCollection("USER_PROFILE") == null) {
            db.createCollection("USER_PROFILE");
        }

        MongoCollection<Document> collection = db.getCollection("USER_PROFILE");

        Document document = new Document();

        document.put("firstName", firstName);
        document.put("lastName", lastName);
        document.put("email", email.toLowerCase());
        document.put("password", password);

        Date date = new Date();
        document.put("createDateTime", date);
        document.put("updateDateTime", date);
        document.put("lastLoginDateTime", date);

        collection.insertOne(document);
        collection.createIndex(new BasicDBObject("email", 1));
        LOGGER.info("User created in DB: " + email);
    }

    /**
     * <h3>The method finds a user given an email.</h3>
     * <p>
     * It is assumed that each user has a unique email address. You cannot have
     * multiple users with the same email
     * address.
     * </p>
     * 
     * @param email
     * @return boolean
     * @throws DBException
     */
    public boolean doesUserExist(String email) throws DBException {

        MongoDatabase db = DBManager.getMongoDB();
        Document document = new Document();
        document.put("email", email.toLowerCase());
        MongoCollection<Document> collection = db.getCollection("USER_PROFILE");

        if (collection.find(document).first() != null)
            return true;
        else
            return false;
    }

    /**
     * <h3>The method finds a user given an email address.</h3>
     * <p>
     * It returns a User object with
     * prefilled data retrieved from the database. The user can be null if the user
     * does not exist in the database.
     * </p>
     * 
     * @param email
     * @return User
     * @throws DBException
     */
    public User findUser(String email) throws DBException {

        User user = null;

        MongoDatabase db = DBManager.getMongoDB();
        Document document = new Document();
        document.put("email", email.toLowerCase());
        MongoCollection<Document> collection = db.getCollection("USER_PROFILE");
        FindIterable<Document> findIterable = collection.find(document);

        Document result = findIterable.first();
        if (result != null) {
            user = new User();
            user.setEmail(result.getString("email"));
            user.setFirstName(result.getString("firstName"));
            user.setLastName(result.getString("lastName"));
            user.setPassword(result.getString("password"));
        }
        return user;
    }

    /**
     * <p>
     * The method finds a user in the database and edits the entry.
     * </p>
     * 
     * @param email
     * @return User
     * @throws DBException
     */
    public User updateUserInDB(User oldInfo, String firstname, String lastname, String email, String password)
            throws DBException {

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

        return jaguarUtils.fillUpUser(firstname, lastname, email, password);

    }

    public void deleteUserFromDB(String email) throws DBException {

        LOGGER.info("Entering deleteUserFromDB");
        MongoDatabase db = DBManager.getMongoDB();
        MongoCollection<Document> collection = db.getCollection("USER_PROFILE");

        Bson query = eq("email", email);
        collection.deleteOne(query);
        LOGGER.info("User deleted: " + email);

    }
}
