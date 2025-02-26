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
    private final List<User> users = new ArrayList<>();

    public void createUsers(User user){
        if(user.getId() == null || user.getEmail() == null || user.getEmail().isEmpty() || user.getId().isEmpty()
        ||user.getPassword() == null || user.getPassword().isEmpty()){
            return;
        }
        if(users.stream().anyMatch(u -> u.getId().equals(user.getId())) ||
                users.stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))){
        return;
        }
        users.add(user);
    }

    public boolean updateUsers(String id,String name,String email,String password){
        if(id == null || id.isEmpty() || name == null || name.isEmpty() || email == null || email.isEmpty() ||
                password == null || password.isEmpty()){
            return false;
        }
        for(User u : users){
            if(u.getId().equals(id)){
                u.setName(name);
                u.setEmail(email);
                u.setPassword(password);
                return  true;
            }
        }
        return false;
    }

    public void removeUsers(User user){
        if(user == null || user.getEmail().isEmpty() || user.getId().isEmpty()){
            return;
        }
        users.remove(user);
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUser(String id){
        for(User u : users){
            if(u.getId().equals(id)){
                return u;
            }
        }
        return null;
    }

}