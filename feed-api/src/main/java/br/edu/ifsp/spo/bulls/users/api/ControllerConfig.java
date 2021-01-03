package br.edu.ifsp.spo.bulls.users.api;

import br.edu.ifsp.spo.bulls.users.api.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerConfig {

    private Logger logger = LoggerFactory.getLogger(ControllerConfig.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handle(ResourceNotFoundException e) {
        logger.error("Returning HTTP 404 Not Found", e);
        throw e;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(ResourceBadRequestException e) {
        logger.error("Returning HTTP 400 Not Found", e);
        throw e;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handle(ResourceConflictException e) {
        logger.error("Returning HTTP 409 Not Found", e);
        throw e;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handle(ResourceForbiddenException e) {
        logger.error("Returning HTTP 403 Not Found", e);
        throw e;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handle(ResourceUnauthorizedException e) {
        logger.error("Returning HTTP 401 Not Found", e);
        throw e;
    }
}


