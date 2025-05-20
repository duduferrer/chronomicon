package bh.app.chronomicon.exception;

import bh.app.chronomicon.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LpnaAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleLpnaAlreadyExistsException(LpnaAlreadyExistsException exception){
        ErrorResponseDTO response = new ErrorResponseDTO(
                HttpStatus.CONFLICT.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException exception){
        ErrorResponseDTO response = new ErrorResponseDTO(
                HttpStatus.NO_CONTENT.value(),
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

}
