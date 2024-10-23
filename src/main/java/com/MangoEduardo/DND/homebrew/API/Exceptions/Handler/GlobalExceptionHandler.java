package com.MangoEduardo.DND.homebrew.API.Exceptions.Handler;

import com.MangoEduardo.DND.homebrew.API.Exceptions.EscuelaMagiaNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Exceptions.EspecieNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Exceptions.HechizoNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Exceptions.SubEspecieNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

}
