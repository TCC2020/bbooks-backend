package br.edu.ifsp.spo.bulls.competition.api.exception;

import br.edu.ifsp.spo.bulls.competition.api.enums.CodeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ResourceForbiddenException extends RuntimeException {
    private static final long serialVersionUID = 2949171135602789718L;
    public CodeException code;

    public ResourceForbiddenException(String message, CodeException code) {
        super(message);
        this.code = code;
    }

    public ResourceForbiddenException(String message) {
        super(message);
    }

    public CodeException getCode() {
        return code;
    }

    public void setCode(CodeException code) {
        this.code = code;
    }
}