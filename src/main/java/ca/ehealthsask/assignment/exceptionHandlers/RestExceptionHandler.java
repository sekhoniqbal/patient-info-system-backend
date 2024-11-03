package ca.ehealthsask.assignment.exceptionHandlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import lombok.Getter;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FormValidationError handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .stream().forEach(fieldError -> {
                    fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
                });
        return new FormValidationError(fieldErrors, "form data contains errors");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleValidationErrors(HttpMessageNotReadableException ex) {

        return new Error("Request payload must be valid json object");
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    public ResponseStatusException handleResponseStatusException(ResponseStatusException ex) {
        return ex;
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ResponseStatusException handleBadCredentialAcception(BadCredentialsException ex) {
        return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }



}

@Getter
class FormValidationError {
    private Map<String, String> fieldErrors;
    private String message;

    public FormValidationError(Map<String, String> errors, String message) {
        this.fieldErrors = errors;
        this.message = message;
    }

}

@Getter
class Error {
    private String message;

    public Error(String message) {
        this.message = message;
    }

}