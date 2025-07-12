package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.enums.ShiftType;

public record CreateOperatorDTO(AtcoDTO user,
								ShiftType shift,
								String id,
								boolean isSupervisor,
								boolean isTrainee,
								boolean isInstructor) {


}