package com.example.controller.errorPage;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ErrorPage {

    @ExceptionHandler(Exception.class)
    public String exception(Exception e) {
        e.printStackTrace();
        return "500";
    }
}
