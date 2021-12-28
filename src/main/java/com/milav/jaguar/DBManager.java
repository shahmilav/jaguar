/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milav.jaguar;


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

//    private void init() throws UnknownHostException {
//        mongo = new MongoClient("localhost");
//        db = mongo.getDB("mydb");
//    }

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
        // uri = new MongoClientURI("mongodb://shahjigar:Curi0usGe0rge@alex.mongohq.com:10005/Q6Nkd2HydZkZHxQndvGIiA");
        //uri = new MongoClientURI("mongodb://pebblenotes:Curi0usGe0rge@ds053136.mlab.com:53136/pebblenotes");
        //uri = new MongoClientURI("mongodb://pebblenotes:Curi0usGe0rge@pebblenotes-shard-00-00.l364q.mongodb.net:27017,pebblenotes-shard-00-01.l364q.mongodb.net:27017,pebblenotes-shard-00-02.l364q.mongodb.net:27017/pebblenotes?replicaSet=atlas-mwhtpx-shard-0&authSource=admin&retryWrites=true&w=majority", MongoClientOptions.builder().sslEnabled(false).sslInvalidHostNameAllowed(true));

        String uri = "mongodb+srv://milav:pulsar66@cluster0.jnvid.mongodb.net/jaguar?retryWrites=true&w=majority";
         
        MongoClient mongoClient = MongoClients.create(uri);
        mongoDB = mongoClient.getDatabase("jaguar");
        
    }
    
    public static void main(String args[]) throws DBException {
        System.out.println(DBManager.getMongoDB().getName());
        System.out.println(DBManager.getMongoDB().getCollection("USER_PROFILE").countDocuments());
    }
}
