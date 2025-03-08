package edu.eci.cvds.ECIReserves;

import edu.eci.cvds.ECIReserves.model.Role;
import edu.eci.cvds.ECIReserves.model.User;
import edu.eci.cvds.ECIReserves.repository.UserRepository;
import edu.eci.cvds.ECIReserves.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class Pruebas {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUsersSuccess(){
        User user = new User("1234", "Pedro", "correo@escuelaing.edu.co", "Password", Role.USER);

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);

        assertTrue(userService.createUsers(user));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void createUsersFailNotEnoughData(){
        User user = new User("1234", "", "correo@escuelaing.edu.co", "Password", Role.USER);

        assertFalse(userService.createUsers(user));
        verify(userRepository, never()).save(any());
    }

    @Test
    public void createUsersFailRepitedData(){
        User user = new User("1234", "Pedro", "correo@escuelaing.edu.co", "Password", Role.USER);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        assertFalse(userService.createUsers(user));
        verify(userRepository, never()).save(any());
    }
}
