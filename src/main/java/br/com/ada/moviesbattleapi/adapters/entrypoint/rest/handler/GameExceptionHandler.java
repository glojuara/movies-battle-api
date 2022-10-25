package br.com.ada.moviesbattleapi.adapters.entrypoint.rest.handler;

import br.com.ada.moviesbattleapi.core.domain.exception.GameAlreadyStartedException;
import br.com.ada.moviesbattleapi.core.domain.exception.GameException;
import br.com.ada.moviesbattleapi.core.domain.exception.GameNotFoundException;
import br.com.ada.moviesbattleapi.core.domain.exception.GameNotStartedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GameExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GameException.class)
    public ResponseEntity handleGameException(GameException ex) {
        return new ResponseEntity<>(buildErrorMessage(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity handleGameNotFoundException(GameNotFoundException ex) {
        return new ResponseEntity<>(buildErrorMessage(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GameAlreadyStartedException.class)
    public ResponseEntity handleGameAlreadyStartedException(GameAlreadyStartedException ex) {
        return new ResponseEntity<>(buildErrorMessage(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GameNotStartedException.class)
    public ResponseEntity handleGameNotStartedException(GameNotStartedException ex) {
        return new ResponseEntity<>(buildErrorMessage(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        return new ResponseEntity<>(buildErrorMessage(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> buildErrorMessage(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return body;
    }

}
