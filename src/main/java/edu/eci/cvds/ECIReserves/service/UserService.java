package edu.eci.cvds.ECIReserves.service;

import edu.eci.cvds.ECIReserves.model.User;
import edu.eci.cvds.ECIReserves.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean createUsers(User user){
        if(user.getId() == null || user.getEmail() == null || user.getEmail().isEmpty() || user.getId().isEmpty()
        ||user.getPassword() == null || user.getPassword().isEmpty()){
            return false;
        }
        if(userRepository.findById(user.getId()).isPresent() || userRepository.findByEmail(user.getEmail()) != null){
        return false;
        }
        userRepository.save(user);
        return true;
    }

    public boolean updateUsers(String id,String name,String email,String password){
        if(id == null || id.isEmpty() || name == null || name.isEmpty() || email == null || email.isEmpty() ||
                password == null || password.isEmpty()){
            return false;
        }

       User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return false;
        }
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return true;
    }

    public void removeUsers(String id){
        if(id == null || id.isEmpty()) {
            return;
        }
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id){
        return userRepository.findById(id).orElse(null);
    }

}