package edu.eci.cvds.ecireserves.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.cvds.ecireserves.dto.UserDTO;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.ApiResponse;
import edu.eci.cvds.ecireserves.model.User;
import edu.eci.cvds.ecireserves.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestParam String email, @RequestParam String password) throws EciReservesException{
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario logueado", authService.login(email, password)));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody UserDTO userDTO) throws EciReservesException{
        return ResponseEntity.ok(new ApiResponse<>(true, "Usuario registrado", authService.register(userDTO)));
    }
}
