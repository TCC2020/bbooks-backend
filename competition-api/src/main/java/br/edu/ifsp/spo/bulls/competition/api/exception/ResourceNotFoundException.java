package br.edu.ifsp.spo.bulls.competition.api.exception;

import br.edu.ifsp.spo.bulls.competition.api.enums.CodeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2952659171804391823L;

    private CodeException code;

    public ResourceNotFoundException(String message, CodeException code) {
        super(message);
        this.code = code;
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public CodeException getCode() {
        return code;
    }

    public void setCode(CodeException code) {
        this.code = code;
    }
}