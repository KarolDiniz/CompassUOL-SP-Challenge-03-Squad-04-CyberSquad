package br.com.compassuol.pb.challenge.msuser.controller;

import br.com.compassuol.pb.challenge.msuser.dto.UserDTO;
import br.com.compassuol.pb.challenge.msuser.entities.User;
import br.com.compassuol.pb.challenge.msuser.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }

    @Test
    void createUser_ValidUserDTO_ReturnsCreatedUser() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("compasstest");
        userDTO.setFirstName("compasstest");
        userDTO.setEmail("compasstest@example.com");

        User createdUser = new User();
        createdUser.setId(1L);
        createdUser.setFirstName("compasstest");
        createdUser.setLastName("compasstest");
        createdUser.setEmail("compasstest@example.com");

        when(userService.createUser(any(UserDTO.class))).thenReturn(createdUser);

        // Act
        ResponseEntity<User> response = userController.createUser(userDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdUser, response.getBody());
    }

    @Test
    void getUserById_UserExists_ReturnsUser() {
        // Arrange
        Long userId = 1L;

        User user = new User();
        user.setId(userId);
        user.setFirstName("compasstest");
        user.setLastName("compasstest");
        user.setEmail("compasstest@example.com");

        when(userService.getUserById(userId)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void updateUser_ValidUserDTO_ReturnsUpdatedUser() {
        // Arrange
        Long userId = 1L;

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("compasstest");
        userDTO.setFirstName("compasstest");
        userDTO.setEmail("compasstest@example.com");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setFirstName("Updated Name");
        updatedUser.setLastName("Updated Name");
        updatedUser.setEmail("updated.email@example.com");

        when(userService.updateUser(eq(userId), any(UserDTO.class))).thenReturn(updatedUser);

        // Act
        ResponseEntity<User> response = userController.updateUser(userId, userDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
    }

    @Test
    void getUserByEmail_UserExists_ReturnsUser() {
        // Arrange
        String userEmail = "john.doe@example.com";

        User user = new User();
        user.setId(1L);
        user.setFirstName("compasstest");
        user.setLastName("compasstest");
        user.setEmail(userEmail);
        ;

        when(userService.findByEmail(userEmail)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userController.getUserByEmail(userEmail);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void getUserByEmail_UserDoesNotExist_ReturnsNotFound() {
        // Arrange
        String userEmail = "nonexistent@example.com";

        when(userService.findByEmail(userEmail)).thenReturn(null);

        // Act
        ResponseEntity<User> response = userController.getUserByEmail(userEmail);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}


