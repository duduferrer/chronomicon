package bh.app.chronomicon.exception;

import bh.app.chronomicon.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflicException(ConflictException exception){
        ErrorResponseDTO response = new ErrorResponseDTO(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(NotFoundException exception){
        ErrorResponseDTO response = new ErrorResponseDTO(
                HttpStatus.NO_CONTENT.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorResponseDTO> handleServerException(ServerException exception){
        ErrorResponseDTO response = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseDTO> handleForbiddenException(ForbiddenException exception){
        ErrorResponseDTO response = new ErrorResponseDTO(
                HttpStatus.FORBIDDEN.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

}
