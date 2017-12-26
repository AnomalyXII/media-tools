package net.anomalyxii.mediatools.api.builders;

import net.anomalyxii.mediatools.api.exceptions.LibraryException;
import net.anomalyxii.mediatools.api.Album;
import net.anomalyxii.mediatools.api.Artist;
import net.anomalyxii.mediatools.api.Library;
import net.anomalyxii.mediatools.api.Song;

import java.io.Serializable;
import java.util.Optional;

/**
 * An interface for managing domain models within a {@link Library} that
 * can also be shared with the {@link LibraryBuilder} that is creating
 * it.
 * <p>
 * Created by Anomaly on 24/02/2017.
 */
public interface LibraryContext<ID extends Serializable> {

    // *********************************
    // Get Methods
    // *********************************

    /**
     * Get an {@link Artist}.
     *
     * @param id the {@link Artist} ID
     * @return the {@link Artist}
     * @throws LibraryException if anything goes wrong or the {@link Artist} is not found
     */
    Artist<ID> getArtist(ID id) throws LibraryException;

    /**
     * Get an {@link Artist} based on their {@link Artist#getName() name}.
     *
     * @param artistName the {@link Artist} name
     * @return the {@link Artist}
     * @throws LibraryException if anything goes wrong or the {@link Artist} is not found
     */
    Artist<ID> getArtistByName(String artistName) throws LibraryException;

    /**
     * Get an {@link Album}.
     *
     * @param id the {@link Album} ID
     * @return the {@link Album}
     * @throws LibraryException if anything goes wrong or the {@link Album} is not found
     */
    Album<ID> getAlbum(ID id) throws LibraryException;

    /**
     * Get an {@link Album} based on it's {@link Album#getTitle() title}.
     *
     * @param artist    the {@link Artist} to whom the {@link Album} belongs
     * @param albumName the {@link Album} title
     * @return the {@link Album}
     * @throws LibraryException if anything goes wrong or the {@link Album} is not found
     */
    Album<ID> getAlbumByArtistAndTitle(Artist<ID> artist, String albumName) throws LibraryException;

    /**
     * Get a {@link Song}.
     *
     * @param id the {@link Song} ID
     * @return the {@link Song}
     * @throws LibraryException if anything goes wrong or the {@link Song} is not found
     */
    Song<ID> getSong(ID id) throws LibraryException;

    /**
     * Get a {@link Song} based on it's
     * {@link Song#getTrackNumber() track no}.
     *
     * @param album   the {@link Album} to which the {@link Song} belongs
     * @param trackNo the {@link Song} track number
     * @return the {@link Song}
     * @throws LibraryException if anything goes wrong or the {@link Song} is not found
     */
    Song<ID> getSongByAlbumAndTrackNumber(Album<ID> album, int trackNo) throws LibraryException;

    // *********************************
    // Find Methods
    // *********************************

    /**
     * Find an {@link Artist}.
     *
     * @param id the {@link Artist} ID
     * @return the {@link Optional} {@link Artist}
     * @throws LibraryException if anything goes wrong
     */
    Optional<Artist<ID>> findArtist(ID id) throws LibraryException;

    /**
     * Find an {@link Artist} based on their {@link Artist#getName() name}.
     *
     * @param artistName the {@link Artist} name
     * @return the {@link Optional} {@link Artist}
     * @throws LibraryException if anything goes wrong
     */
    Optional<Artist<ID>> findArtistByName(String artistName) throws LibraryException;

    /**
     * Find an {@link Album}.
     *
     * @param id the {@link Album} ID
     * @return the {@link Optional} {@link Album}
     * @throws LibraryException if anything goes wrong
     */
    Optional<Album<ID>> findAlbum(ID id) throws LibraryException;

    /**
     * Find an {@link Album} based on it's {@link Album#getTitle() title}.
     *
     * @param artist    the {@link Artist} to whom the {@link Album} belongs
     * @param albumName the {@link Album} title
     * @return the {@link Optional} {@link Album}
     * @throws LibraryException if anything goes wrong
     */
    Optional<Album<ID>> findAlbumByArtistAndTitle(Artist<ID> artist, String albumName) throws LibraryException;

    /**
     * Find a {@link Song}.
     *
     * @param id the {@link Song} ID
     * @return the {@link Optional} {@link Song}
     * @throws LibraryException if anything goes wrong
     */
    Optional<Song<ID>> findSong(ID id) throws LibraryException;

    /**
     * Find a {@link Song} based on it's
     * {@link Song#getTrackNumber() track no}.
     *
     * @param album   the {@link Album} to which the {@link Song} belongs
     * @param trackNo the {@link Song} track number
     * @return the {@link Optional} {@link Song}
     * @throws LibraryException if anything goes wrong
     */
    Optional<Song<ID>> findSongByAlbumAndTrackNumber(Album<ID> album, int trackNo) throws LibraryException;

    // *********************************
    // Create Methods
    // *********************************

    ArtistBuilder<ID> newArtistBuilder();

    AlbumBuilder<ID> newAlbumBuilder();

    SongBuilder<ID> newSongBuilder();

    // *********************************
    // Save Methods
    // *********************************

    /**
     * Save an {@link Artist} into the context.
     */
    Artist<ID> save(Artist<ID> artist);

    /**
     * Save an {@link Album} into the context.
     */
    Album<ID> save(Album<ID> album);
    
    /**
     * Save a {@link Song} into the context.
     */
    Song<ID> save(Song<ID> song);

}
