package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.enums.Role;
import jakarta.validation.constraints.NotBlank;

public record CreateSystemUserDTO(
		@NotBlank
		Role role
) {
}

