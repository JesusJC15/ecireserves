package edu.eci.cvds.ECIReserves.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.eci.cvds.ECIReserves.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
