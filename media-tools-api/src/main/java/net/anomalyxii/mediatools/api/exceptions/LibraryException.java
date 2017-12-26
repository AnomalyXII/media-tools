package net.anomalyxii.mediatools.api.exceptions;

import net.anomalyxii.mediatools.api.Library;

/**
 * An {@link Exception} that is thrown by library-related
 * functions; usually indicates a problem building or
 * refreshing a {@link Library}
 * <p>
 * Created by Anomaly on 18/02/2017.
 */
public class LibraryException extends MediaException {

    // *********************************
    // Constructors
    // *********************************

    public LibraryException() {
    }

    public LibraryException(String message) {
        super(message);
    }

    public LibraryException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibraryException(Throwable cause) {
        super(cause);
    }

    public LibraryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
