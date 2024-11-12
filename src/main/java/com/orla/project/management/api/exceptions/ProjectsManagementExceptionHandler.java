package com.orla.project.management.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ProjectsManagementExceptionHandler {

  @ExceptionHandler(ProjectAlreadyExists.class)
  public ResponseEntity<Object> handleProjectAlreadyExistsException(ProjectAlreadyExists ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    body.put("statusCode", HttpStatus.CONFLICT.value());
    body.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
    return new ResponseEntity<>(body, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, Object> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach((FieldError error) -> {
      String errorMessage = error.getDefaultMessage();
      errors.put("message", errorMessage);
      errors.put("statusCode", HttpStatus.BAD_REQUEST.value());
      errors.put("timestamp", ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
    });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }
}
