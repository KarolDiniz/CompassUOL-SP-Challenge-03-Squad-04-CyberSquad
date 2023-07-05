package br.com.compassuol.pb.challenge.msuser.dto;

import br.com.compassuol.pb.challenge.msuser.entities.RoleName;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoleDTO {

    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleName name;
}
