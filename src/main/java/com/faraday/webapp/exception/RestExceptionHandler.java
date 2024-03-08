package com.faraday.webapp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.faraday.webapp.dto.response.ErrorDTORes;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorDTORes> notFoundException(Exception ex){

    ErrorDTORes error = new ErrorDTORes();
    error.setDate(null);
    error.setCode(HttpStatus.NOT_FOUND.value());
    error.setMessage(ex.getMessage());

    return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.NOT_FOUND);

  }

  @ExceptionHandler(BindingException.class)
  public ResponseEntity<ErrorDTORes> bindingException(Exception ex){

    ErrorDTORes error = new ErrorDTORes();
    error.setDate(null);
    error.setCode(HttpStatus.BAD_REQUEST.value());
    error.setMessage(ex.getMessage());

    return new ResponseEntity<ErrorDTORes>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);

  }

  @ExceptionHandler(DuplicateException.class)
  public ResponseEntity<ErrorDTORes> duplicateException(Exception ex){

    ErrorDTORes error = new ErrorDTORes();
    error.setDate(null);
    error.setCode(HttpStatus.NOT_ACCEPTABLE.value());
    error.setMessage(ex.getMessage());
    
    return new ResponseEntity<ErrorDTORes>(error, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);

  }

}