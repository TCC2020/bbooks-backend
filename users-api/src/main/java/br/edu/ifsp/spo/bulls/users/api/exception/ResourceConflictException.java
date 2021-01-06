package br.edu.ifsp.spo.bulls.users.api.exception;

import br.edu.ifsp.spo.bulls.common.api.enums.CodeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException {
    private static final long serialVersionUID = 252538959719425669L;

	public CodeException code;

	public ResourceConflictException(String message, CodeException code) {
		super(message);
		this.code = code;
	}

	public ResourceConflictException(String message) {
		super(message);
	}

	public CodeException getCode() {
		return code;
	}

	public void setCode(CodeException code) {
		this.code = code;
	}
}