package net.anomalyxii.mediatools.api.builders;

import net.anomalyxii.mediatools.api.Artist;

import java.io.Serializable;

/**
 * A builder interface for an {@link Artist}.
 *
 * Created by Anomaly on 18/12/2017.
 */
public interface ArtistBuilder<ID extends Serializable> {

    // *********************************
    // Builder Methods
    // *********************************

    /**
     * Set the name of the {@link Artist}.
     *
     * @param name the name
     * @return the {@link ArtistBuilder}, for chaining
     */
    ArtistBuilder<ID> withName(String name);

    // *********************************
    // Build Methods
    // *********************************

    /**
     * Build the {@link Artist}.
     *
     * @return the {@link Artist}
     */
    Artist<ID> build();

}
