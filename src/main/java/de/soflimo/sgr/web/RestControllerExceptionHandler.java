package de.soflimo.sgr.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 */
@ControllerAdvice
public class RestControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestControllerExceptionHandler.class);


    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(Throwable.class)
    public void handleException (final Exception e, final HttpServletRequest request) {
        log.error(e.getMessage(), e);
    }
}
