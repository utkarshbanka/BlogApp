package com.Blogging.Blogging.Exception;

import com.Blogging.Blogging.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourcenotfoundException(ResourceNotFoundException ex)
    {
        String message = ex.getMessage();
        ApiResponse  apiResponse = new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentException(MethodArgumentNotValidException ex)
    {
          Map<String,String> as = new HashMap<>();

          ex.getBindingResult().getAllErrors().forEach((error)->{
              String fieldName = ((FieldError) error).getField();
              String message = error.getDefaultMessage();
              as.put(fieldName,message);
          });
          return new ResponseEntity<Map<String,String>>(as,HttpStatus.BAD_REQUEST);
    }
}
