package net.anomalyxii.mediatools.api;

import java.io.Serializable;

/**
 * An Artist.
 * <p>
 * Created by Anomaly on 15/04/2016.
 */
public interface Artist<ID extends Serializable> {

    // *********************************
    // Interface Methods
    // *********************************

    /**
     * Get the {@code ID} of the {@code Artist}
     *
     * @return the {@code ID}
     */
    ID getId();

    /**
     * Get the {@code Name} of the {@code Artist}
     *
     * @return the {@code Name}
     */
    String getName();

}
