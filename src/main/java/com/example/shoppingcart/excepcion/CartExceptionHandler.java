package com.example.shoppingcart.excepcion;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CartExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleStudentNotFoundException(NotFoundException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponseDto.builder().
                status(404)
                .code(ex.getCode())
                .message(ex.getMessage())
                .detail(ex.getDetails())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponseDto> handle(MethodArgumentNotValidException ex, WebRequest request) {
        ex.printStackTrace();
        List<ConstraintsViolationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ConstraintsViolationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponseDto responseDto = ErrorResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code("ARGUMENT_VALIDATION_FAILED")
                .message(ex.getMessage())
                .detail(ex.getMessage())
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .build();

        validationErrors.forEach(validation -> responseDto.getData().put(validation.getProperty(), validation.getMessage()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }
}
