package edu.eci.cvds.ecireserves.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.cvds.ecireserves.dto.UserDTO;
import edu.eci.cvds.ecireserves.model.User;
import edu.eci.cvds.ecireserves.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRol(userDTO.getRol());

        return userRepository.save(user);
    }

    public User updateUser(String id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElse(null);
        if(user != null) {
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword()); 
         
            return userRepository.save(user);
        }else {
            return null;
        }
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElse(null);
        if(user != null) {
            userRepository.delete(user);
        }
    }
}
