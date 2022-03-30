package br.com.raospower.app.exceptions;

import br.com.raospower.app.exceptions.base.BaseException;
import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends BaseException {

    private static final HttpStatus status =  HttpStatus.NOT_FOUND;

    public RoleNotFoundException(String message) {
        super(message, status);
    }
}
