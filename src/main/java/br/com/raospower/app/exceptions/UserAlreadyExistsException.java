package br.com.raospower.app.exceptions;

import br.com.raospower.app.exceptions.base.BaseException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BaseException {

    private static final HttpStatus status =  HttpStatus.BAD_REQUEST;

    public UserAlreadyExistsException(String message) {
        super(message, status);
    }
}
