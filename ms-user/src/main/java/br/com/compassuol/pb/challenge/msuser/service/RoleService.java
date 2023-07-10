package br.com.compassuol.pb.challenge.msuser.service;

import br.com.compassuol.pb.challenge.msuser.dto.RoleDTO;
import br.com.compassuol.pb.challenge.msuser.entities.Role;
import br.com.compassuol.pb.challenge.msuser.repository.RoleRepository;
import br.com.compassuol.pb.challenge.msuser.entities.RoleName;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;
    public Role createRole(RoleDTO roleDTO) {
        RoleName roleName = roleDTO.getName();

        if (!isValidRoleName(roleName)) {
            throw new IllegalArgumentException("Invalid role name: " + roleName);
        }

        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }

    private boolean isValidRoleName(RoleName roleName) {
        return Arrays.asList(RoleName.values()).contains(roleName);
    }


    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
