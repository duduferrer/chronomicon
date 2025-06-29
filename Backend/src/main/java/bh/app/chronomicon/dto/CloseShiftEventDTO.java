package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.entities.UserEntity;

import java.sql.Timestamp;

public record CloseShiftEventDTO(
        String details,
        Timestamp end,
        UserEntity last_updated_by,
        Timestamp updated_at
) {
}
