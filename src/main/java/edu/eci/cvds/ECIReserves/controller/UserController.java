package edu.eci.cvds.ECIReserves.controller;

import edu.eci.cvds.ECIReserves.model.User;
import edu.eci.cvds.ECIReserves.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{name}")
    public User getUserByName( @PathVariable String name) {
        return userService.getUser(name);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        userService.createUsers(user);
        return user;
    }

    @PutMapping("/{id}")
    public boolean updateUser(@PathVariable String id, @RequestBody User user) {
        return userService.updateUsers(id, user.getName(), user.getEmail(), user.getPassword());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.removeUsers(id);
    }
}
