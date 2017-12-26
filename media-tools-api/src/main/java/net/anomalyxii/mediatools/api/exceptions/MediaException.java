package net.anomalyxii.mediatools.api.exceptions;

/**
 * A generic {@link Exception} that indicates a problem
 * within the suite of {@code media-tools}.
 * <p>
 * Created by Anomaly on 18/02/2017.
 */
public class MediaException extends Exception {

    // *********************************
    // Constructors
    // *********************************

    public MediaException() {
    }

    public MediaException(String message) {
        super(message);
    }

    public MediaException(String message, Throwable cause) {
        super(message, cause);
    }

    public MediaException(Throwable cause) {
        super(cause);
    }

    public MediaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
