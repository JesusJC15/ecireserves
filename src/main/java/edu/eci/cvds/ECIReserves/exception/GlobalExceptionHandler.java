package edu.eci.cvds.ECIReserves.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import edu.eci.cvds.ECIReserves.model.ApiResponse;
import lombok.Generated;

@RestControllerAdvice
@Generated
public class GlobalExceptionHandler {

    @ExceptionHandler(EciReservesException.class)
    public ResponseEntity<ApiResponse<Void>> handleEciReservesException(EciReservesException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false, e.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, e.getMessage(), null));
    }
}
