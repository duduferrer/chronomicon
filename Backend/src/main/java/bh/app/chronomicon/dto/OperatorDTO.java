package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.enums.ShiftType;

import java.time.Duration;

public record OperatorDTO(
        String id,
        String lpna,
        Duration workload,
        ShiftType shift,
        boolean isSpvs,
        boolean isInst,
        boolean isTrainee

) {


}
