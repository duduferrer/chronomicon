package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.entities.AtcoEntity;

import java.sql.Timestamp;

public record CloseShiftEventDTO(
        String details,
        Timestamp end,
        AtcoEntity last_updated_by,
        Timestamp updated_at
) {
}
