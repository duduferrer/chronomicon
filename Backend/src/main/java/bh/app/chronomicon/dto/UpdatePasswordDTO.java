package bh.app.chronomicon.dto;

import bh.app.chronomicon.security.ValidPassword;

public record UpdatePasswordDTO(
        @ValidPassword
        String newPassword,
        String oldPassword
) {
}
