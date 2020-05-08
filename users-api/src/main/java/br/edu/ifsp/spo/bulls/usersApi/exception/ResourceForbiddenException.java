package br.edu.ifsp.spo.bulls.usersApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ResourceForbiddenException extends RuntimeException {
    private static final long serialVersionUID = 2949171135602789718L;
}