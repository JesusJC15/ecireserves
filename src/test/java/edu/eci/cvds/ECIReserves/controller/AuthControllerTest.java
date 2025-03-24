package edu.eci.cvds.ecireserves.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.eci.cvds.ecireserves.dto.UserDTO;
import edu.eci.cvds.ecireserves.enums.UserRole;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.ApiResponse;
import edu.eci.cvds.ecireserves.model.User;
import edu.eci.cvds.ecireserves.service.AuthService;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO("1", "John Doe", "john@example.com", "password", UserRole.ESTUDIANTE);
        user = new User("1", "John Doe", "john@example.com", "encryptedPassword", UserRole.ESTUDIANTE);
    }

    @SuppressWarnings("null")
    @Test
    void register_ShouldReturnCreatedUser() throws EciReservesException {
        when(authService.register(userDTO)).thenReturn(user);

        ResponseEntity<ApiResponse<User>> response = authController.register(userDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals(user.getEmail(), response.getBody().getData().getEmail());
        verify(authService, times(1)).register(userDTO);
    }

    @Test
    void register_ShouldThrowException_WhenEmailAlreadyExists() throws EciReservesException {
        when(authService.register(userDTO)).thenThrow(new EciReservesException("El email ya está en uso"));

        EciReservesException exception = assertThrows(EciReservesException.class, () -> {
            authController.register(userDTO);
        });

        assertEquals("El email ya está en uso", exception.getMessage());
        verify(authService, times(1)).register(userDTO);
    }

    @SuppressWarnings("null")
    @Test
    void login_ShouldReturnUser_WhenCredentialsAreCorrect() throws EciReservesException {
        when(authService.login(user.getEmail(), userDTO.getPassword())).thenReturn(user);

        ResponseEntity<ApiResponse<User>> response = authController.login(userDTO.getEmail(), userDTO.getPassword());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals(user.getEmail(), response.getBody().getData().getEmail());
        verify(authService, times(1)).login(user.getEmail(), userDTO.getPassword());
    }

    @Test
    void login_ShouldThrowException_WhenInvalidCredentials() throws EciReservesException {
        when(authService.login(user.getEmail(), userDTO.getPassword()))
                .thenThrow(new EciReservesException("Credenciales inválidas"));

        EciReservesException exception = assertThrows(EciReservesException.class, () -> {
            authController.login(userDTO.getEmail(), userDTO.getPassword());
        });

        assertEquals("Credenciales inválidas", exception.getMessage());
        verify(authService, times(1)).login(user.getEmail(), userDTO.getPassword());
    }
}
