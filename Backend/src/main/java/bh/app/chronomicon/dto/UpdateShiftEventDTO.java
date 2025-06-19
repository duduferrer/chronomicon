package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.entities.UserEntity;
import bh.app.chronomicon.model.enums.EventType;

import java.sql.Timestamp;

public record UpdateShiftEventDTO(
        EventType type,
        String details,
        Timestamp start,
        Timestamp end,
        Timestamp updated_at,
        UserEntity last_updated_by
) {
}
