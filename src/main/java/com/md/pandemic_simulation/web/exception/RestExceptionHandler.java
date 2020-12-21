package com.md.pandemic_simulation.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException ex) {

        String logInfo = String.format(" %s. %s ", ex.getLocalizedMessage(), ex.getClass().getSimpleName());
        log.error(logInfo, ex);

        return new ResponseEntity<>(new ApiError(ex.getMessage()), NOT_FOUND);
    }
}

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
class ApiError {
    String error;
}
