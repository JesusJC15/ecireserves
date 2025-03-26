package edu.eci.cvds.ecireserves.controller;

import edu.eci.cvds.ecireserves.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.cvds.ecireserves.dto.AuthRequest;
import edu.eci.cvds.ecireserves.dto.UserDTO;
import edu.eci.cvds.ecireserves.model.ApiResponse;
import edu.eci.cvds.ecireserves.repository.UserRepository;
import edu.eci.cvds.ecireserves.service.CustomUserDetailsService;
import edu.eci.cvds.ecireserves.util.JwtUtil;


@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail()).orElse(null);
        if (user != null) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, "El email ya está registrado", null));
        }else {
            user = new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getRol());
            userRepository.save(user);
            return ResponseEntity.ok(new ApiResponse<>(true, "Usuario registrado exitosamente", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> loginUser(@RequestBody AuthRequest authRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new ApiResponse<>(true, jwt, null));
    }
}
