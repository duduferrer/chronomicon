package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.enums.Shift;

public record CreateOperatorDTO(UserDTO user,
                                Shift shift,
                                String id,
                                boolean isSupervisor,
                                boolean isTrainee,
                                boolean isInstructor) {


}