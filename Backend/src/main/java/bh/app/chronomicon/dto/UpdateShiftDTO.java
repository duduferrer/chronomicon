package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.entities.OperatorEntity;
import bh.app.chronomicon.model.entities.SectorizationEntity;
import bh.app.chronomicon.model.entities.ShiftEventsEntity;
import bh.app.chronomicon.model.enums.ShiftType;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

public record UpdateShiftDTO(
        LocalDate date,
        ShiftType shift,
        List<SectorizationEntity> sectorization,
        List<ShiftEventsEntity> events,
        List<OperatorEntity> operators
) {
}
