package bh.app.chronomicon.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(int status, String message, LocalDateTime timeStamp) {
}
