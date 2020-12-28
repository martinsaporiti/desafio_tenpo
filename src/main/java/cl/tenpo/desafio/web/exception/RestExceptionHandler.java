package cl.tenpo.desafio.web.exception;

import cl.tenpo.desafio.exception.BadCredentialsException;
import cl.tenpo.desafio.exception.DuplicateUsernameException;
import cl.tenpo.desafio.exception.UserNotFoundException;
import cl.tenpo.desafio.web.model.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by martin.saporiti
 * on 25/12/2020
 * Github: https://github.com/martinsaporiti
 */
@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     * Método para gestionar los errores de validación en los request.
     * @param ex
     * @return Map<String, String>
     */
    @ExceptionHandler()
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            log.error(errorMessage);
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleException(DuplicateUsernameException exc) {
        ErrorResponseDto error = new ErrorResponseDto(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler()
    public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException exc) {
        ErrorResponseDto error = new ErrorResponseDto(HttpStatus.FORBIDDEN.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler()
    public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(BadCredentialsException exc) {
        ErrorResponseDto error = new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleException(UserNotFoundException exc) {
        ErrorResponseDto error = new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleException(HttpMessageNotReadableException exc) {
        ErrorResponseDto error = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(),
                "Error en el formato de los datos ingresados",
                System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleException(Exception exc) {
        ErrorResponseDto error = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
