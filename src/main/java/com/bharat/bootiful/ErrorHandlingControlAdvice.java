package com.bharat.bootiful;
/*
 * @author bharat.verma
 * @created Saturday, 01 April 2023
 */

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlingControlAdvice {
    @ExceptionHandler(IllegalStateException.class)
    ProblemDetail handleIllegalStateException (IllegalStateException exception) {
        var problemDetail =  ProblemDetail.forStatus(HttpStatusCode.valueOf(404));
        problemDetail.setDetail("The name must start with a CAPITAL letter! ");
        return problemDetail;
    }
}