package bh.app.chronomicon.dto;

import bh.app.chronomicon.security.ValidPassword;

public record RecoverPasswordDTO(
        @ValidPassword
        String newPassword
) {
}
