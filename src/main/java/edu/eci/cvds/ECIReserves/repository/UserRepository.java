package edu.eci.cvds.ECIReserves.repository;

import edu.eci.cvds.ECIReserves.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.eci.cvds.ECIReserves.model.User;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    List<User> findByName(String name);
    List<User> findByRole(Role role);
}
