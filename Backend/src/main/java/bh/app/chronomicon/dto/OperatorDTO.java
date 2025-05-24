package bh.app.chronomicon.dto;

import bh.app.chronomicon.model.enums.Shift;

import java.time.Duration;

public record OperatorDTO(
        String id,
        UserDTO user,
        Duration workload,
        Shift shift

) {


}
