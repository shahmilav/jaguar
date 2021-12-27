package com.milav.jaguar;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Date;
import java.util.HashMap;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@Controller
public class JaguarApplication {

    private final static String validUsername = "me@milav.com";
    private final static String validPassword = "milav";
    private boolean isUserLoggedIn = false;

    @Autowired
    private UserRepository repository;

    public static void main(String[] args) throws DBException {
        JaguarApplication app = new JaguarApplication();
        app.createUserInDB();
        
        //app.createUser();
        SpringApplication.run(JaguarApplication.class, args);
    }

    private void createUser() {
        repository.save(new User("me@milav.com", "my-secret-password"));
    }

    private void createUserInDB() throws DBException {

        MongoDatabase db = DBManager.getMongoDB();

        if (db.getCollection("USER_PROFILE") == null) {
            db.createCollection("USER_PROFILE");
        }

        MongoCollection coll = db.getCollection("USER_PROFILE");

        Document doc = new Document();
        doc.put("firstName", "Milav");
        doc.put("lastName", "Shah");
        doc.put("email", "me@milav.com");
        doc.put("password", "mysecretpassword");

        Date date = new Date();
        doc.put("createDateTime", date);
        doc.put("updateDateTime", date);
        doc.put("lastLoginDateTime", date);

        coll.insertOne(doc);
        coll.createIndex(new BasicDBObject("email", 1));
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam(name = "email") String username,
            @RequestParam(name = "password") String password, Model model) {
        System.out.println(username);
        System.out.println(password);

        if ((username.equalsIgnoreCase(validUsername)) && (password.equals(validPassword))) {
            isUserLoggedIn = true;
            return "redirect:/dashboard";
        } else {
            return "index";
        }
    }

    @GetMapping("/dashboard")
    public String validateUser(Model model) {
        if (isUserLoggedIn) {
            model.addAttribute("name", "Welcome to Jaguar Dashboard, " + validUsername);
            return "dashboard";
        } else {
            return "redirect:/index";
        }
    }

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("title", "Login Page");
        return "index";
    }
}
