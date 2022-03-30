package br.com.raospower.app.exceptions;

import br.com.raospower.app.exceptions.base.BaseException;
import org.springframework.http.HttpStatus;

public class PermissionNotInformedException extends BaseException {

    private static final HttpStatus status =  HttpStatus.BAD_REQUEST;

    public PermissionNotInformedException(String message) {
        super(message, status);
    }
}
