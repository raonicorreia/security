package br.com.raospower.app.exceptions.base;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handler(TokenExpiredException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(baseErrorBuilder(HttpStatus.UNAUTHORIZED, exception.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handler(AccessDeniedException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(baseErrorBuilder(HttpStatus.UNAUTHORIZED, exception.getMessage()));
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handler(BaseException exception) {
        return ResponseEntity
                .status(exception.getErroStatus())
                .body(baseErrorBuilder(exception.getErroStatus(), exception.getMessage()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handler(Throwable exception) {
        logger.error("[ControllerAdvice] Throwable: ", exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(baseErrorBuilder(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
    }

    private ErrorResponse baseErrorBuilder(HttpStatus httpStatus, String message) {
        return new ErrorResponse(httpStatus.value(), httpStatus.name(),
                message, new Date(), null);
    }

}
