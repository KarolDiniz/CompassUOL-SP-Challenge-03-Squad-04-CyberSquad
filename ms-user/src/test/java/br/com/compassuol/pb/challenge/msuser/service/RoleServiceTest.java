package br.com.compassuol.pb.challenge.msuser.service;

import br.com.compassuol.pb.challenge.msuser.dto.RoleDTO;
import br.com.compassuol.pb.challenge.msuser.entities.Role;
import br.com.compassuol.pb.challenge.msuser.entities.RoleName;
import br.com.compassuol.pb.challenge.msuser.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RoleServiceTest {

    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleService = new RoleService(roleRepository);
    }

    @Test
    void createRole_ValidRoleDTO_ReturnsCreatedRole() {
        // Arrange
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(RoleName.ROLE_ADMIN);

        Role createdRole = new Role();
        createdRole.setId(1L);
        createdRole.setName(RoleName.ROLE_ADMIN);

        when(roleRepository.save(any(Role.class))).thenReturn(createdRole);

        // Act
        Role result = roleService.createRole(roleDTO);

        // Assert
        assertNotNull(result);
        assertEquals(createdRole.getId(), result.getId());
        assertEquals(createdRole.getName(), result.getName());
    }

    @Test
    void getAllRoles_RolesExist_ReturnsListOfRoles() {
        // Arrange
        Role role1 = new Role(1L, RoleName.ROLE_ADMIN);
        Role role2 = new Role(2L, RoleName.ROLE_OPERATOR);
        List<Role> roles = Arrays.asList(role1, role2);

        when(roleRepository.findAll()).thenReturn(roles);

        // Act
        List<Role> result = roleService.getAllRoles();

        // Assert
        assertNotNull(result);
        assertEquals(roles.size(), result.size());
        assertTrue(result.containsAll(roles));
    }

    @Test
    void getAllRoles_NoRoles_ReturnsEmptyList() {
        // Arrange
        when(roleRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Role> result = roleService.getAllRoles();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
