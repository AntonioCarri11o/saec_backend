package com.saec.formtic.interceptor;

import com.saec.formtic.utils.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {

    //Esta clase se encarga de que los DTOs apliquen sus limitaciones sin necesitadad de estarllamando
    //esta clase cada que lo necesitemos ya que lo hace automatico solo colcanto la anotacion @Valid

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse<?>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        String firstError = ex.getBindingResult().getFieldError().getDefaultMessage();

        CustomResponse<?> response = new CustomResponse<>(
                400,
                firstError,
                true,
                null
        );

        return ResponseEntity.badRequest().body(response);
    }
}
