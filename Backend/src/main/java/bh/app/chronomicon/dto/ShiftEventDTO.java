package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.enums.EventType;

import java.sql.Timestamp;

public record ShiftEventDTO(
        String id,
        EventType type,
        String details,
        Timestamp start,
        Timestamp end
) {
}
