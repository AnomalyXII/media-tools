package net.anomalyxii.mediatools.api;

import java.io.Serializable;

/**
 * An extension of a {@link Library} that will raise events when the
 * state of the {@link Library} changes. Other components can listen for
 * these state changes by attaching a {@link LibraryObserver} to the
 * {@link Library}.
 *
 * @param <ID> the type of ID used by the {@link Library}
 */
public interface ObservableLibrary<ID extends Serializable> extends Library<ID> {

    // *********************************
    // Interface Methods
    // *********************************

    /**
     * Add a {@link LibraryObserver}.
     *
     * @param observer the {@link LibraryObserver} to add
     */
    void addObserver(LibraryObserver<ID> observer);

    /**
     * Remove a {@link LibraryObserver}.
     *
     * @param observer the {@link LibraryObserver} to remove
     */
    void removeObserver(LibraryObserver<ID> observer);

    // *********************************
    // Default Methods
    // *********************************

    /**
     * Add an {@link ArtistAddedObserver}.
     *
     * @param observer the {@link ArtistAddedObserver}
     */
    default void addArtistAddedObserver(ArtistAddedObserver<ID> observer) {
        addObserver(observer);
    }

    /**
     * Add an {@link ArtistRemovedObserver}.
     *
     * @param observer the {@link ArtistRemovedObserver}
     */
    default void addArtistRemovedObserver(ArtistRemovedObserver<ID> observer) {
        removeObserver(observer);
    }

    /**
     * Add an {@link AlbumAddedObserver}.
     *
     * @param observer the {@link AlbumAddedObserver}
     */
    default void addAlbumAddedObserver(AlbumAddedObserver<ID> observer) {
        addObserver(observer);
    }

    /**
     * Add an {@link AlbumRemovedObserver}.
     *
     * @param observer the {@link AlbumRemovedObserver}
     */
    default void addAlbumRemovedObserver(AlbumRemovedObserver<ID> observer) {
        removeObserver(observer);
    }

    /**
     * Add a {@link SongAddedObserver}.
     *
     * @param observer the {@link SongAddedObserver}
     */
    default void addSongAddedObserver(SongAddedObserver<ID> observer) {
        addObserver(observer);
    }

    /**
     * Add a {@link SongRemovedObserver}.
     *
     * @param observer the {@link SongRemovedObserver}
     */
    default void addSongRemovedObserver(SongRemovedObserver<ID> observer) {
        removeObserver(observer);
    }

    // *********************************
    // Observer Interfaces
    // *********************************

    /**
     * An observer that can be notified of changes to the state of a
     * {@link Library}.
     *
     * @param <ID> the type of ID used by the {@link Library}
     */
    interface LibraryObserver<ID extends Serializable> {

        /**
         * Fired when an {@link Artist} is added to a {@link Library}.
         *
         * @param library the {@link Library} that has changed
         * @param artist  the {@link Artist} that was added
         */
        default void onArtistAdded(Library<ID> library, Artist<ID> artist) {
            // Default: no-op
        }

        /**
         * Fired when an {@link Artist} is removed from a {@link Library}.
         *
         * @param library the {@link Library} that has changed
         * @param artist  the {@link Artist} that was removed
         */
        default void onArtistRemoved(Library<ID> library, Artist<ID> artist) {
            // Default: no-op
        }

        /**
         * Fired when an {@link Album} is added to a {@link Library}.
         *
         * @param library the {@link Library} that has changed
         * @param album   the {@link Album} that was added
         */
        default void onAlbumAdded(Library<ID> library, Album<ID> album) {
            // Default: no-op
        }

        /**
         * Fired when an {@link Album} is removed from a {@link Library}.
         *
         * @param library the {@link Library} that has changed
         * @param album   the {@link Album} that was added
         */
        default void onAlbumRemoved(Library<ID> library, Album<ID> album) {
            // Default: no-op
        }

        /**
         * Fired when a {@link Song} is added to a {@link Library}.
         *
         * @param library the {@link Library} that has changed
         * @param song    the {@link Song} that was added
         */
        default void onSongAdded(Library<ID> library, Song<ID> song) {
            // Default: no-op
        }

        /**
         * Fired when a {@link Song} is added to a {@link Library}.
         *
         * @param library the {@link Library} that has changed
         * @param song    the {@link Song} that was added
         */
        default void onSongRemoved(Library<ID> library, Song<ID> song) {
            // Default: no-op
        }

    }

    /**
     * A specialized {@link LibraryObserver} for observing the
     * {@link #onArtistAdded(Library, Artist)} state change.
     *
     * @param <ID> the type of ID used by the {@link Library}
     */
    @FunctionalInterface
    interface ArtistAddedObserver<ID extends Serializable> extends LibraryObserver<ID> {

        @Override
        void onArtistAdded(Library<ID> library, Artist<ID> artist);

    }

    /**
     * A specialized {@link LibraryObserver} for observing the
     * {@link #onArtistRemoved(Library, Artist)} state change.
     *
     * @param <ID> the type of ID used by the {@link Library}
     */
    @FunctionalInterface
    interface ArtistRemovedObserver<ID extends Serializable> extends LibraryObserver<ID> {

        @Override
        void onArtistRemoved(Library<ID> library, Artist<ID> artist);

    }

    /**
     * A specialized {@link LibraryObserver} for observing the
     * {@link onAlbumAdded(Library, Album)} state change.
     *
     * @param <ID> the type of ID used by the {@link Library}
     */
    @FunctionalInterface
    interface AlbumAddedObserver<ID extends Serializable> extends LibraryObserver<ID> {

        @Override
        void onAlbumAdded(Library<ID> library, Album<ID> album);

    }

    /**
     * A specialized {@link LibraryObserver} for observing the
     * {@link #onAlbumRemoved(Library, Album)} state change.
     *
     * @param <ID> the type of ID used by the {@link Library}
     */
    @FunctionalInterface
    interface AlbumRemovedObserver<ID extends Serializable> extends LibraryObserver<ID> {

        @Override
        void onAlbumRemoved(Library<ID> library, Album<ID> album);

    }

    /**
     * A specialized {@link LibraryObserver} for observing the
     * {@link #onSongAdded(Library, Song)} state change.
     *
     * @param <ID> the type of ID used by the {@link Library}
     */
    @FunctionalInterface
    interface SongAddedObserver<ID extends Serializable> extends LibraryObserver<ID> {

        @Override
        void onSongAdded(Library<ID> library, Song<ID> song);

    }

    /**
     * A specialized {@link LibraryObserver} for observing the
     * {@link #onSongRemoved(Library, Song)} state change.
     *
     * @param <ID> the type of ID used by the {@link Library}
     */
    @FunctionalInterface
    interface SongRemovedObserver<ID extends Serializable> extends LibraryObserver<ID> {

        @Override
        void onSongRemoved(Library<ID> library, Song<ID> song);

    }

}
