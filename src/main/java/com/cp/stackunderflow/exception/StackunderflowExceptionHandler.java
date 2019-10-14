package com.cp.stackunderflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class StackunderflowExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception e, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(1000, "Unable to process");
        return new ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(StackunderflowException.class)
    public final ResponseEntity handleStackunderflowExceptions(StackunderflowException se, WebRequest request) {

        ErrorMessage errorMessage = new ErrorMessage(se.getStatusCode(), se.getStatusMessage());

        if (se.getStatusCode().equals(1001) || se.getStatusCode().equals(1024))
            return new ResponseEntity(errorMessage, HttpStatus.UNAUTHORIZED);
        else if(se.getStatusCode().equals(1034))
            return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
