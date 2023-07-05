package br.com.compassuol.pb.challenge.msuser.dto;

import br.com.compassuol.pb.challenge.msuser.entities.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String firstName;

    @NotNull
    @Size(min = 3)
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 6)
    private String password;

    private List<Role> roles;
}
