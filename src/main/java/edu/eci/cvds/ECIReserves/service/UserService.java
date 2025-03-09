package edu.eci.cvds.ecireserves.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.cvds.ecireserves.dto.UserDTO;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.User;
import edu.eci.cvds.ecireserves.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) throws EciReservesException {
        return userRepository.findById(id).orElseThrow(() -> new EciReservesException(EciReservesException.USER_NOT_FOUND));
    }

    public List<User> getUsersByName(String name) {
        return userRepository.findByName(name);
    }

    public User createUser(UserDTO userDTO) throws EciReservesException {
        if(userRepository.findById(userDTO.getId()).isPresent()){
            throw new EciReservesException(EciReservesException.USER_ALREADY_EXISTS);
        }else if(userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EciReservesException(EciReservesException.USER_EMAIL_ALREADY_EXISTS);
        }else{
            User user = new User();
            user.setId(userDTO.getId());
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setRol(userDTO.getRol());
    
            return userRepository.save(user);
        }
    }

    public User updateUser(String id, UserDTO userDTO) throws EciReservesException {
        User user = userRepository.findById(id).orElseThrow(() -> new EciReservesException(EciReservesException.USER_NOT_FOUND));
        if(userDTO.getName() != null) user.setName(userDTO.getName());
        if(userDTO.getEmail() != null && !user.getEmail().equals(userDTO.getEmail())) {
            if(userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
                throw new EciReservesException(EciReservesException.USER_ALREADY_EXISTS);
            }
            user.setEmail(userDTO.getEmail());
        }
        if(userDTO.getPassword() != null) user.setPassword(userDTO.getPassword());

        return userRepository.save(user);
    }

    public void deleteUser(String id) throws EciReservesException {
        if(!userRepository.existsById(id)){
            throw new EciReservesException(EciReservesException.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }
}
