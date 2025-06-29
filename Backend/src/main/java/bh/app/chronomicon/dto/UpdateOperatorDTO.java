package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.enums.ShiftType;

public record UpdateOperatorDTO(
        ShiftType shiftType,
        Boolean isSupervisor,
        Boolean isTrainee,
        Boolean isInstructor
) {

}
