package org.cachestudy.store;

public class StoreAccessException extends Throwable {
    public StoreAccessException() {
    }

    public StoreAccessException(String message) {
        super(message);
    }

    public StoreAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoreAccessException(Throwable cause) {
        super(cause);
    }

    public StoreAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
