package br.edu.ifsp.spo.bulls.usersApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ResourceUnauthorizedException  extends RuntimeException{
    private static final long serialVersionUID = 7043168303302085862L;

    public ResourceUnauthorizedException(String message) {
        super(message);
    }

}
