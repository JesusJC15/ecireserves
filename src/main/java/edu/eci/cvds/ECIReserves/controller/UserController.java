package edu.eci.cvds.ecireserves.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.cvds.ecireserves.dto.UserDTO;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.ApiResponse;
import edu.eci.cvds.ecireserves.model.User;
import edu.eci.cvds.ecireserves.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuarios obtenidos exitosamente", userService.getAllUsers()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable("id") String id) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario con id: " + id, userService.getUserById(id)));
    }

    @GetMapping("/users/search")
    public ResponseEntity<ApiResponse<List<User>>> getUsersByName(@RequestParam String name) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuarios con nombre: " + name , userService.getUsersByName(name)));
    }
    @GetMapping("/users/email")
    public ResponseEntity<ApiResponse<Optional<User>>> getUserByEmail(@RequestParam String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Usuario no encontrado", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario encontrado", user));
    }
    @PostMapping("/users")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody UserDTO userDTO) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario creado", userService.createUser(userDTO)));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable("id") String id, @RequestBody UserDTO userDTO) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario actualizado", userService.updateUser(id, userDTO)));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable("id") String id) throws EciReservesException {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario eliminado", null));
    }


}