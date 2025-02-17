package edu.eci.cvds.ecireserves.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.eci.cvds.ecireserves.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // This interface is used to interact with the database
}
