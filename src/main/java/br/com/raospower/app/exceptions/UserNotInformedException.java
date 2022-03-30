package br.com.raospower.app.exceptions;

import br.com.raospower.app.exceptions.base.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotInformedException extends BaseException {

    private static final HttpStatus status =  HttpStatus.BAD_REQUEST;

    public UserNotInformedException(String message) {
        super(message, status);
    }
}
