package com.milav.jaguar;

/**
 *
 * @author milavshah
 */

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByEmail(String email);

    public List<User> findByLastName(String lastName);

}