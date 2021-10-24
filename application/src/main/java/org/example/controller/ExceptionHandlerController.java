package org.example.controller;

import org.example.exception.BlockedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionHandlerController {


    @ExceptionHandler(BlockedException.class)
    public void handleBlockedException(BlockedException ex, HttpServletResponse response) throws IOException {
        System.out.println("handleBlockedException");
      //  response.sendError(HttpStatus.SERVICE_UNAVAILABLE.value(), "Access denied for some time");
        response.sendRedirect("/error");
    }

}

