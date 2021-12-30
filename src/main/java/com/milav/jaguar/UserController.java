package com.milav.jaguar;

import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Service;

@Service
public class UserController {

    /**
     * The method creates an entry in the database with the given information.
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
    }

    /**
     * The method finds a user given an email. It is assumed that each user has a
     * unique email address. You cannot have multiple users with the same email
     * address.
     * 
     * @param email
     * @return boolean
     * @throws DBException
     */
    public boolean doesUserExist(String email) throws DBException {

        boolean result = false;

        MongoDatabase db = DBManager.getMongoDB();
        Document document = new Document();
        document.put("email", email.toLowerCase());
        MongoCollection<Document> collection = db.getCollection("USER_PROFILE");
        if (collection.find(document).first() != null)
            result = true;

        return result;
    }

    /**
     * The method finds a user given an email address. It returns a User object with
     * prefilled data retrieved from the database.
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

}
