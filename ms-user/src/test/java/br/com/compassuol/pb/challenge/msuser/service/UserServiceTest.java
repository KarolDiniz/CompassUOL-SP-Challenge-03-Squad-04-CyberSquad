package br.com.compassuol.pb.challenge.msuser.service;
import br.com.compassuol.pb.challenge.msuser.dto.UserDTO;
import br.com.compassuol.pb.challenge.msuser.entities.Role;
import br.com.compassuol.pb.challenge.msuser.entities.RoleName;
import br.com.compassuol.pb.challenge.msuser.entities.User;
import br.com.compassuol.pb.challenge.msuser.repository.RoleRepository;
import br.com.compassuol.pb.challenge.msuser.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_shouldCreateUserAndSendNotification() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setRoles(new ArrayList<>());

        User savedUser = new User();
        savedUser.setEmail(userDTO.getEmail());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User createdUser = userService.createUser(userDTO);

        // Assert
        assertNotNull(createdUser);
        assertEquals(userDTO.getEmail(), createdUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
        verify(rabbitTemplate, times(1)).convertAndSend(eq("ms-notification"), stringArgumentCaptor.capture());
        assertEquals(userDTO.getEmail(), stringArgumentCaptor.getValue());
    }



    @Test
    void setRoles_shouldSetUserRoles() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        Role role1 = new Role(1L, RoleName.ROLE_ADMIN);
        Role role2 = new Role(2L, RoleName.ROLE_OPERATOR);
        userDTO.setRoles(List.of(role1, role2));

        User user = new User();

        when(roleRepository.findAllById(anyList())).thenReturn(List.of(role1, role2));

        // Act
        userService.setRoles(userDTO, user);

        // Assert
        assertEquals(2, user.getRoles().size());
        assertTrue(user.getRoles().contains(role1));
        assertTrue(user.getRoles().contains(role2));
        verify(roleRepository, times(1)).findAllById(anyList());
    }


    @Test
    void setRoles_shouldClearUserRolesWhenEmpty() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setRoles(new ArrayList<>());

        User user = new User();
        user.setRoles(List.of(new Role()));

        // Act
        userService.getClass().getDeclaredMethod("setRoles", UserDTO.class, User.class).setAccessible(true);
        userService.getClass().getDeclaredMethod("setRoles", UserDTO.class, User.class).invoke(userService, userDTO, user);

        // Assert
        assertNull(user.getRoles());
    }

    @Test
    void findByEmail_shouldReturnUserWithEmail() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(user);

        // Act
        User foundUser = userService.findByEmail(email);

        // Assert
        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testUpdateUser() {
        // Dados de exemplo
        Long userId = null;
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("CompassoUser");
        userDTO.setLastName("CompassoUser");
        userDTO.setEmail("CompassoUser@example.com");

        // Simulação do comportamento do repositório
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFirstName("Old");
        existingUser.setLastName("Name");
        existingUser.setEmail("oldemail@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Chamada do método de serviço
        User updatedUser = userService.updateUser(userId, userDTO);

        // Verificações
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        Assertions.assertEquals(userId, capturedUser.getId());
        Assertions.assertEquals(userDTO.getFirstName(), capturedUser.getFirstName());
        Assertions.assertEquals(userDTO.getLastName(), capturedUser.getLastName());
        Assertions.assertEquals(userDTO.getEmail(), capturedUser.getEmail());
        // Outras verificações necessárias

        // Verifica se o método getUserById foi chamado uma vez
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserById_shouldReturnUserWithMatchingId() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.getUserById(userId);

        // Assert
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        verify(userRepository, times(1)).findById(userId);
    }

}


