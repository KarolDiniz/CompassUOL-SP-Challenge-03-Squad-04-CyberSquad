package br.com.compassuol.pb.challenge.msuser.controller;

import br.com.compassuol.pb.challenge.msuser.dto.RoleDTO;
import br.com.compassuol.pb.challenge.msuser.entities.Role;
import br.com.compassuol.pb.challenge.msuser.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody RoleDTO roleDTO) {
        Role role = roleService.createRole(roleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
