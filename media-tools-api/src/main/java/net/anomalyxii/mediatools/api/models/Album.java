package net.anomalyxii.mediatools.api.models;

import java.io.Serializable;

/**
 * An Album.
 * <p>
 * Created by Anomaly on 15/04/2016.
 */
public interface Album<ID extends Serializable> {

    // ******************************
    // Interface Methods
    // ******************************

    /**
     * Get the {@code ID} of the {@code Album}
     *
     * @return the {@code ID}
     */
    ID getId();

    /**
     * Get the {@code Title} of the {@code Album}
     *
     * @return the album {@code Title}
     */
    String getTitle();

    /**
     * Get the {@link Artist} that is associated with the
     * {@code Album} as a whole.
     *
     * @return the {@link Artist}
     */
    Artist<ID> getAlbumArtist();

}
