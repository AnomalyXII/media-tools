package net.anomalyxii.mediatools.api.builders;

import net.anomalyxii.mediatools.api.Album;
import net.anomalyxii.mediatools.api.Artist;

import java.io.Serializable;

/**
 * A builder interface for an {@link Album}.
 *
 * Created by Anomaly on 18/12/2017.
 */
public interface AlbumBuilder<ID extends Serializable> {

    // *********************************
    // Builder Methods
    // *********************************

    /**
     * Set the title of the {@link Album}.
     *
     * @param name the title
     * @return the {@link AlbumBuilder}, for chaining
     */
    AlbumBuilder<ID> withTitle(String name);

    /**
     * Set the {@link Artist} of the {@link Album}.
     *
     * @param artist the {@link Artist}
     * @return the {@link AlbumBuilder}, for chaining
     */
    AlbumBuilder<ID> withArtist(Artist<ID> artist);

    // *********************************
    // Build Methods
    // *********************************

    /**
     * Build the {@link Album}.
     *
     * @return the {@link Album}
     */
    Album<ID> build();

}
