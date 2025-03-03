package edu.eci.cvds.ECIReserves.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    void setUp(){
        user = new User();
    }

    @Test
    void shouldCreateUser(){
        user.setId("123456789");
        assertEquals("123456789", user.getId());
        user.setName("Juan");
        assertEquals("Juan", user.getName());
        user.setEmail("juan.prueba@correo.com");
        assertEquals("juan.prueba@correo.com", user.getEmail());
        user.setPassword("123456");
        assertEquals("123456", user.getPassword());
        user.setRol(UserRole.STUDENT);
        assertEquals(UserRole.STUDENT, user.getRol());
    }
}
