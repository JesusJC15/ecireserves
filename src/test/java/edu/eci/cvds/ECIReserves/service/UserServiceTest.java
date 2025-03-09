package edu.eci.cvds.ecireserves.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.eci.cvds.ecireserves.dto.UserDTO;
import edu.eci.cvds.ecireserves.enums.UserRole;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.User;
import edu.eci.cvds.ecireserves.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User("1", "John Doe", "johndoe@example.com", "password123", UserRole.ESTUDIANTE);
        userDTO = new UserDTO("1", "John Doe", "johndoe@example.com", "password123", UserRole.ESTUDIANTE);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> users = userService.getAllUsers();

        assertFalse(users.isEmpty());
        assertEquals(1, users.size());
        assertEquals(user.getId(), users.get(0).getId());
    }

    @Test
    void testGetUserById_Success() throws EciReservesException {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById("1");

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById("2")).thenReturn(Optional.empty());

        EciReservesException exception = assertThrows(EciReservesException.class, () -> userService.getUserById("2"));

        assertEquals(EciReservesException.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testCreateUser_Success() throws EciReservesException {
        when(userRepository.findById("1")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("johndoe@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
        assertEquals(user.getEmail(), createdUser.getEmail());
    }

    @Test
    void testCreateUser_AlreadyExists() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        EciReservesException exception = assertThrows(EciReservesException.class, () -> userService.createUser(userDTO));

        assertEquals(EciReservesException.USER_ALREADY_EXISTS, exception.getMessage());
    }
}
