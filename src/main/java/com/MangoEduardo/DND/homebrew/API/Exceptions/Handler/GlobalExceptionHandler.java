package com.MangoEduardo.DND.homebrew.API.Exceptions.Handler;

import com.MangoEduardo.DND.homebrew.API.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(HechizoNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleHechizoNotFoundException(HechizoNotFoundException e) {
        Map<String,String> errores = new HashMap<>();

        errores.put("Error",e.getMessage());
        errores.put("Timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errores,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EscuelaMagiaNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleEscuelaMagiaNotFoundException(EscuelaMagiaNotFoundException e) {
        Map<String,String> errores = new HashMap<>();

        errores.put("Error",e.getMessage());
        errores.put("Timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errores,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String,String>> handleHttpMessageNotReadeableException(HttpMessageNotReadableException e) {
        Map<String,String> errores = new HashMap<>();

        errores.put("Error",e.getMessage());
        errores.put("Timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EspecieNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleEspecieNotFoundException(EspecieNotFoundException e) {
        Map<String,String> errores = new HashMap<>();

        errores.put("Error",e.getMessage());
        errores.put("Timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errores,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SubEspecieNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleSubEspecieNotFoundException(SubEspecieNotFoundException e) {
        Map<String,String> errores = new HashMap<>();

        errores.put("Error",e.getMessage());
        errores.put("Timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(errores,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", e.getMessage());
        errors.put("Timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();

        //Indices para extraer el valor de la excepcion
        int firstDotIndex = e.getMessage().indexOf("'");
        int secondIndex = e.getMessage().indexOf("'", firstDotIndex + 1);
        String errorMessage = e.getMessage();

        if(errorMessage.contains("Duplicate entry")){
            errorMessage = "Ya existe un registro con el valor: " + errorMessage.substring(firstDotIndex + 1, secondIndex);
        }

        errors.put("Error", errorMessage);
        errors.put("Timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}

