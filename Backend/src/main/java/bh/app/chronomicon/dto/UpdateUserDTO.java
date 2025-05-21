package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.model.enums.Rank;

public record UpdateUserDTO(
        String lpna_identifier,
        String full_name,
        Rank rank,
        String service_name,
        Boolean supervisor,
        Boolean instructor,
        Boolean trainee
) {}
