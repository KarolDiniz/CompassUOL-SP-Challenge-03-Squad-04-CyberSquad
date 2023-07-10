package br.com.compassuol.pb.challenge.msuser.service;

import br.com.compassuol.pb.challenge.msuser.dto.UserDTO;
import br.com.compassuol.pb.challenge.msuser.entities.*;
import br.com.compassuol.pb.challenge.msuser.repository.RoleRepository;
import br.com.compassuol.pb.challenge.msuser.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RabbitTemplate rabbitTemplate;

    public User createUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        setRoles(userDTO, user);
        User savedUser = userRepository.save(user);

        String mensagem = user.getEmail();
        rabbitTemplate.convertAndSend("ms-notification", mensagem);

        return savedUser;
    }

    public User updateUser(Long userId, UserDTO user) {
        User existingUser = getUserById(userId);
        BeanUtils.copyProperties(user, existingUser, "userId");
        return userRepository.save(existingUser);
    }

    private void setRoles(UserDTO userDTO, User user) {
        List<Role> roles = userDTO.getRoles();
        if (roles != null && !roles.isEmpty()) {
            List<Long> roleIds = roles.stream()
                    .map(Role::getId)
                    .collect(Collectors.toList());
            List<Role> fetchedRoles = roleRepository.findAllById(roleIds);
            if (fetchedRoles.size() != roleIds.size()) {
                throw new IllegalArgumentException("One or more roles not found");
            }
            user.setRoles(fetchedRoles);
        } else {
            user.setRoles(null);
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }
}