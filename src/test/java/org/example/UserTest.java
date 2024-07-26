package org.example;

import org.example.dao.impl.UserDAO;
import org.example.entities.User;
import org.example.services.impl.UserService;
import org.example.utils.exception.ValidatorException;
import org.example.utils.validation.impl.UserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserTest {
    @Mock
    private UserDAO userDAO;
    @InjectMocks
    private UserService userService;
    @Mock
    private UserValidation userValidation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void readAll(){
        List<User> userList = new ArrayList<>();
        when(userDAO.readAll()).thenReturn(userList);
        List<User> users = userService.findAll();
        verify(userDAO, times(1)).readAll();
        assertEquals(userList, users);
    }

    @Test
    public void readById(){
        Long id = 1L;
        User user = new User();
        when(userDAO.readById(id)).thenReturn(user);
        User result = userService.findById(id);
        verify(userDAO, times(1)).readById(id);
        assertEquals(user, result);
    }
    @Test
    public void testSaveWithInvalidParams() {

        User user = new User("Sanjar", "Totliboyev", true);

        when(userValidation.isValidForCreate(user)).thenReturn(false);

        ValidatorException exception = assertThrows(ValidatorException.class, () -> {
            userService.save(user);
        });

        exception.getMessage();

        verify(userValidation, times(1)).isValidForCreate(user);
        verify(userDAO, never()).create(any(User.class));

        // Assert the exception message
        assertEquals("Invalid user to create", exception.getMessage());
    }

    @Test
    public void testSaveWithValidParams() {

        User user = new User("Ozodbek", "Adkhamov", true);

        when(userValidation.isValidForCreate(user)).thenReturn(true);
        when(userDAO.create(user)).thenReturn(user);

        User savedUser = userService.save(user);

        verify(userValidation, times(1)).isValidForCreate(user);
        verify(userDAO, times(1)).create(user);

        assertEquals(user, savedUser);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        when(userValidation.isValidForUpdate(user)).thenReturn(true);
        when(userDAO.update(user)).thenReturn(user);

        User updatedUser = userService.update(user);

        verify(userDAO, times(1)).update(user);
        assertEquals(user, updatedUser);
    }

    @Test
    public void testDelete() {
        when(userDAO.deleteById(1L)).thenReturn(true);

        Boolean result = userService.delete(1L);

        verify(userDAO, times(1)).deleteById(1L);
        assertEquals(true, result);
    }
}
