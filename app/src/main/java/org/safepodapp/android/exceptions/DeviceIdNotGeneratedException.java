package org.safepodapp.android.exceptions;

/**
 * Created by Prajit on 3/7/2016.
 */
public class DeviceIdNotGeneratedException extends Exception {
    public DeviceIdNotGeneratedException() {
    }

    public DeviceIdNotGeneratedException(String detailMessage) {
        super(detailMessage);
    }

    public DeviceIdNotGeneratedException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DeviceIdNotGeneratedException(Throwable throwable) {
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
