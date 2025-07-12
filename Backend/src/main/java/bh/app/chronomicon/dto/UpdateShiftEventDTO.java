package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.entities.AtcoEntity;
import bh.app.chronomicon.model.enums.EventType;

import java.sql.Timestamp;

public record UpdateShiftEventDTO(
        EventType type,
        String details,
        Timestamp eventStart,
        Timestamp eventEnd,
        Timestamp updated_at,
        AtcoEntity last_updated_by
) {
}
