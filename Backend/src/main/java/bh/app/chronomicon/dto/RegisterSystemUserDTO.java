package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.enums.Role;
import jakarta.validation.constraints.NotBlank;

public record RegisterSystemUserDTO(
        @NotBlank
        Role role,
        @NotBlank
        String lpna,
        @NotBlank
        String emailAddress,
        @NotBlank
        String saram
){
}
