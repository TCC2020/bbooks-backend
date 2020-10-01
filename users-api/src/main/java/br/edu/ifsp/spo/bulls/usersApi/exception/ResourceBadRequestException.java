package br.edu.ifsp.spo.bulls.usersApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceBadRequestException extends RuntimeException {
    private static final long serialVersionUID = 7131239641050255960L;

	public ResourceBadRequestException(String message) {
		super(message);
	}

}