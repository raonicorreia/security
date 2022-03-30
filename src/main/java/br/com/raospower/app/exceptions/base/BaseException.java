package br.com.raospower.app.exceptions.base;

import org.springframework.http.HttpStatus;

public class BaseException extends Exception {

    private final HttpStatus erroStatus;

    public BaseException(String message) {
        super(message);
        this.erroStatus = HttpStatus.BAD_REQUEST;
    }

    public BaseException(HttpStatus erroStatus) {
        this.erroStatus = erroStatus;
    }

    public BaseException(String message, HttpStatus erroStatus) {
        super(message);
        this.erroStatus = erroStatus;
    }

    public HttpStatus getErroStatus() {
        return erroStatus;
    }
}
