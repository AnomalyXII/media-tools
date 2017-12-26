package net.anomalyxii.mediatools.api;

import java.io.Serializable;

/**
 * A Song.
 * <p>
 * Created by Anomaly on 15/04/2016.
 */
public interface Song<ID extends Serializable> {

    // *********************************
    // Interface Methods
    // *********************************

    /**
     * Get the {@code ID} of the {@code Song}.
     *
     * @return the {@code ID}
     */
    ID getId();

    /**
     * Get the {@code Track Number} of the {@code Song}.
     *
     * @return the {@code Track Number}
     */
    int getTrackNumber();

    /**
     * Get the {@code Title} of the {@code Song}.
     *
     * @return the {@code Title}
     */
    String getTitle();

    /**
     * Get the {@link Artist} that is associated with the
     * {@code Song}.
     *
     * @return the {@link Artist}
     */
    Artist<ID> getArtist();

    /**
     * Get the {@link Album} that is associated with the
     * {@code Song}.
     *
     * @return the {@link Album}
     */
    Album<ID> getAlbum();

}
