package net.anomalyxii.mediatools.api.builders;

import net.anomalyxii.mediatools.api.Album;
import net.anomalyxii.mediatools.api.Song;
import net.anomalyxii.mediatools.api.Artist;

import java.io.Serializable;

/**
 * A builder interface for an {@link Song}.
 * <p>
 * Created by Anomaly on 18/12/2017.
 */
public interface SongBuilder<ID extends Serializable> {

    // *********************************
    // Builder Methods
    // *********************************

    /**
     * Set the track number of the {@link Song}.
     *
     * @param track the track number
     * @return the {@link SongBuilder}, for chaining
     */
    SongBuilder<ID> withTrackNumber(int track);

    /**
     * Set the title of the {@link Song}.
     *
     * @param name the title
     * @return the {@link SongBuilder}, for chaining
     */
    SongBuilder<ID> withTitle(String name);

    /**
     * Set the {@link Artist} of the {@link Song}.
     *
     * @param artist the {@link Artist}
     * @return the {@link SongBuilder}, for chaining
     */
    SongBuilder<ID> withArtist(Artist<ID> artist);

    /**
     * Set the {@link Album} of the {@link Song}.
     *
     * @param album the {@link Album}
     * @return the {@link SongBuilder}, for chaining
     */
    SongBuilder<ID> withAlbum(Album<ID> album);

    // *********************************
    // Build Methods
    // *********************************

    /**
     * Build the {@link Song}.
     *
     * @return the {@link Song}
     */
    Song<ID> build();

}
