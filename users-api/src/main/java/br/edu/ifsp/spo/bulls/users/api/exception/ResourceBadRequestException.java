package br.edu.ifsp.spo.bulls.users.api.exception;

import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceBadRequestException extends RuntimeException {
    private static final long serialVersionUID = 7131239641050255960L;

    public CodeException code;
	public ResourceBadRequestException(String message, CodeException code) {
		super(message);
		this.code = code;
	}

	public ResourceBadRequestException(String message) {
		super(message);
	}

	public CodeException getCode() {
		return code;
	}

	public void setCode(CodeException code) {
		this.code = code;
	}
}