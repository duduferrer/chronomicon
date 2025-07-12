package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.entities.AtcoEntity;
import bh.app.chronomicon.model.enums.Rank;
import jakarta.validation.constraints.NotBlank;

public record CreateAtcoDTO(
        String lpna_identifier,
        boolean supervisor,
        boolean instructor,
        boolean trainee
) {

}