package br.edu.ifsp.spo.bulls.users.api.exception;

import br.edu.ifsp.spo.bulls.users.api.enums.CodeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ResourceUnauthorizedException  extends RuntimeException{
    private static final long serialVersionUID = 7043168303302085862L;
    private CodeException code ;

    public ResourceUnauthorizedException(String message, CodeException code) {
        super(message);
        this.code = code;
    }

    public ResourceUnauthorizedException(String message) {
        super(message);
    }

    public CodeException getCode() {
        return code;
    }

    public void setCode(CodeException code) {
        this.code = code;
    }
}
