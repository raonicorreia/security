package br.com.raospower.app.security.cryption;

public class CryptionException extends Exception {

    public CryptionException() {
    }

    public CryptionException(String message) {
        super(message);
    }

    public CryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CryptionException(Throwable cause) {
        super(cause);
    }

    public CryptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
