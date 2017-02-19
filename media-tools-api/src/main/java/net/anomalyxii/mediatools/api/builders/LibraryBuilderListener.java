package net.anomalyxii.mediatools.api.builders;

import net.anomalyxii.mediatools.api.models.Album;
import net.anomalyxii.mediatools.api.models.Artist;
import net.anomalyxii.mediatools.api.models.Library;
import net.anomalyxii.mediatools.api.models.Song;

import java.io.Serializable;

/**
 * A listener that will be notified of various events that occur
 * during the building of a {@link Library}.
 * <p>
 * Created by Anomaly on 19/02/2017.
 */
public interface LibraryBuilderListener<ID extends Serializable> {

    // ******************************
    // Interface Methods
    // ******************************

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
     * Called when a new {@link Artist} is added to the
     * {@link Library}.
     *
     * @param library the {@link Library} that the {@link Artist) was added to
     * @param artist  the {@link Artist}
     */
    void onArtistAdded(Library<ID> library, Artist<ID> artist);

    /**
     * Called at the end of the build process if a previously
     * registered {@link Artist} is no longer included in the
     * {@link Library}.
     *
     * @param library the {@link Library} that the {@link Artist) was removed from
     * @param artist  the {@link Artist}
     */
    void onArtistRemoved(Library<ID> library, Artist<ID> artist);

    /**
     * Called when a new {@link Album} is added to the
     * {@link Library}.
     *
     * @param library the {@link Library} that the {@link Album} was added to
     * @param album   the {@link Album}
     */
    void onAlbumAdded(Library<ID> library, Album<ID> album);

    /**
     * Called at the end of the build process if a previously
     * registered {@link Album} is no longer included in the
     * {@link Library}.
     *
     * @param library the {@link Library} that the {@link Album} was removed from
     * @param album   the {@link Album}
     */
    void onAlbumRemoved(Library<ID> library, Album<ID> album);

    /**
     * Called when a new {@link Song} is added to the
     * {@link Library}.
     *
     * @param library the {@link Library} that the {@link Song} was added to
     * @param song    the {@link Song}
     */
    void onSongAdded(Library<ID> library, Song<ID> song);

    /**
     * Called at the end of the build process if a previously
     * registered {@link Song} is no longer included in the
     * {@link Library}.
     *
     * @param library the {@link Library} that the {@link Song} was removed from
     * @param song    the {@link Song}
     */
    void onSongRemoved(Library<ID> library, Song<ID> song);

    /**
     * Called if processing encounters a non-fatal
     * {@link Exception}.
     *
     * @param library the {@link Library} that was being processed when the error occurred
     * @param e       the {@link Exception}
     */
    void onError(Library<ID> library, Exception e);

}
