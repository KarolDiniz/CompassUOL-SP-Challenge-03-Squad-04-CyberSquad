package br.com.compassuol.pb.challenge.msuser.controller;

import br.com.compassuol.pb.challenge.msuser.dto.RoleDTO;
import br.com.compassuol.pb.challenge.msuser.entities.Role;
import br.com.compassuol.pb.challenge.msuser.entities.RoleName;
import br.com.compassuol.pb.challenge.msuser.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RoleControllerTest {

    private RoleController roleController;

    @Mock
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleController = new RoleController(roleService);
    }

    @Test
    void createRole_ValidRoleDTO_ReturnsCreatedRole() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(RoleName.ROLE_ADMIN);

        Role createdRole = new Role();
        createdRole.setId(1L);
        createdRole.setName(RoleName.ROLE_ADMIN);
        when(roleService.createRole(any(RoleDTO.class))).thenReturn(createdRole);

        ResponseEntity<Role> response = roleController.createRole(roleDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdRole, response.getBody());
    }

    @Test
    void getAllRoles_RolesExist_ReturnsListOfRoles() {
        Role role1 = new Role(1L, RoleName.ROLE_ADMIN);
        Role role2 = new Role(2L, RoleName.ROLE_OPERATOR);
        List<Role> roles = Arrays.asList(role1, role2);
        when(roleService.getAllRoles()).thenReturn(roles);

        ResponseEntity<List<Role>> response = roleController.getAllRoles();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roles, response.getBody());
    }
}
