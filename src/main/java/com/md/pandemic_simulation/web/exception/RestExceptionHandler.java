package com.md.pandemic_simulation.web.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {

        String logInfo = String.format(" %s. %s ", ex.getLocalizedMessage(), ex.getClass().getSimpleName());
        log.error(logInfo, ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String logInfo = String.format(" %s. %s ", ex.getLocalizedMessage(), ex.getClass().getSimpleName());
        log.warn(logInfo, ex);
        List<ErrorModel> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorModel(err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                .distinct()
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("VALIDATION ERRORS")
                .status(BAD_REQUEST.value())
                .errorMessage(errorMessages)
                .build();
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }
}


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ErrorResponse {
    private String message;
    private int status;
    private List<ErrorModel> errorMessage;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ErrorModel {
    private String fieldName;
    private Object rejectedValue;
    private String messageError;
}
