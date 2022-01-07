package com.example.web.error;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * created by Atiye Mousavi
 * Date: 9/14/2021
 * Time: 8:44 AM
 */

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler(){super();}


    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final WebRequest request){
        final String bodyOfResponse="This should be application specific";
        return handleExceptionInternal(ex,bodyOfResponse,new HttpHeaders(), HttpStatus.BAD_REQUEST,request);
    }
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex,final WebRequest request){
        final String bodyOfResponse="This should be application specific";
        return handleExceptionInternal(ex,bodyOfResponse,new HttpHeaders(),HttpStatus.BAD_REQUEST,request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String bodyOfResponse="This should be application specific";

        return handleExceptionInternal(ex, bodyOfResponse,headers, HttpStatus.BAD_REQUEST, request);
    }
}
