package net.anomalyxii.mediatools.api.builders;

import net.anomalyxii.mediatools.api.Library;

import java.io.Serializable;

/**
 * A listener that will be notified of various events that occur
 * during the building of a {@link Library}.
 * <p>
 * Created by Anomaly on 19/02/2017.
 */
public interface LibraryBuilderListener<ID extends Serializable> {

    // *********************************
    // Interface Methods
    // *********************************

    /**
     * Called immediately on after creating an empty
     * {@link Library}.
     *
     * @param library the newly initialised {@link Library}
     */
    void onBuildStarted(Library<ID> library);

    /**
     * Called immediately before a built {@link Library} is
     * returned.
     *
     * @param library the finished {@link Library}
     */
    void onBuildFinished(Library<ID> library);

    /**
     * Called if processing encounters a non-fatal
     * {@link Exception}.
     *
     * @param library the {@link Library} that was being processed when the error occurred
     * @param e       the {@link Exception}
     */
    void onError(Library<ID> library, Exception e);

}
