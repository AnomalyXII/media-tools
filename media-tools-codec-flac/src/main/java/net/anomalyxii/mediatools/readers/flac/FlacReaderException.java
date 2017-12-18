package net.anomalyxii.mediatools.readers.flac;

import java.io.IOException;

/**
 * Created by Anomaly on 24/04/2016.
 */
public class FlacReaderException extends IOException {

    // ******************************
    // Members
    // ******************************

    // ******************************
    // Constructors
    // ******************************

    public FlacReaderException() {
    }

    public FlacReaderException(String message) {
        super(message);
    }

    public FlacReaderException(String message, Throwable cause) {
        super(message, cause);
    }

}
