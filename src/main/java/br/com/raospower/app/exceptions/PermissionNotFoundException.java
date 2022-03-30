package br.com.raospower.app.exceptions;

import br.com.raospower.app.exceptions.base.BaseException;
import org.springframework.http.HttpStatus;

public class PermissionNotFoundException extends BaseException {

    private static final HttpStatus status =  HttpStatus.NOT_FOUND;

    public PermissionNotFoundException(String message) {
        super(message, status);
    }
}
