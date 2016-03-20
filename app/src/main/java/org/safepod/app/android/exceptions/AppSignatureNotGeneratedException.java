package org.safepod.app.android.exceptions;

/**
 * Created by Prajit on 3/7/2016.
 */
public class AppSignatureNotGeneratedException extends Exception {
    public AppSignatureNotGeneratedException() {
    }

    public AppSignatureNotGeneratedException(String detailMessage) {
        super(detailMessage);
    }

    public AppSignatureNotGeneratedException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public AppSignatureNotGeneratedException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Throwable getCause() {
        return super.getCause();
    }
}
