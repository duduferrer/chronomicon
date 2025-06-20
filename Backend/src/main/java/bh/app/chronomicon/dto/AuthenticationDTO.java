package bh.app.chronomicon.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank
        String saram,
        @NotBlank
        String password
) {
}
