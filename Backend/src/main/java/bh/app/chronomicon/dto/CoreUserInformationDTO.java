package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.enums.Rank;
import bh.app.chronomicon.model.enums.Role;
import bh.app.chronomicon.security.ValidRole;
import jakarta.validation.constraints.NotBlank;

public record CoreUserInformationDTO(
        @NotBlank
        String emailAddress,
        @NotBlank
        String saram,
        @NotBlank
        String fullName,
        @NotBlank
        String serviceName,
        @NotBlank
        Rank rank,
        String phoneNumber
        
){
}
