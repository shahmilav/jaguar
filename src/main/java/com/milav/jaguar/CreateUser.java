package com.milav.jaguar;

import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class CreateUser {

    public void createUserInDB(String firstName, String lastName, String email, String password) throws DBException {

        MongoDatabase db = DBManager.getMongoDB();

        if (db.getCollection("USER_PROFILE") == null) {
            db.createCollection("USER_PROFILE");
        }

        MongoCollection collection = db.getCollection("USER_PROFILE");

        Document document = new Document();
        document.put("firstName", firstName);
        document.put("lastName", lastName);
        document.put("email", email);
        document.put("password", password);

        Date date = new Date();
        document.put("createDateTime", date);
        document.put("updateDateTime", date);
        document.put("lastLoginDateTime", date);

        collection.insertOne(document);
        collection.createIndex(new BasicDBObject("email", 1));
    }

}
