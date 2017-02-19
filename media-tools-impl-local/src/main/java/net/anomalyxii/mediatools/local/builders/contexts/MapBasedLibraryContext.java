package net.anomalyxii.mediatools.local.builders.contexts;

import net.anomalyxii.mediatools.api.Album;
import net.anomalyxii.mediatools.api.Artist;
import net.anomalyxii.mediatools.api.Song;
import net.anomalyxii.mediatools.api.builders.LibraryContext;
import net.anomalyxii.mediatools.api.exceptions.LibraryException;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A {@link LibraryContext} that is backed by several {@link Map}
 * instances.
 *
 * @param <ID> the type of ID of instances stored in this context
 */
public class MapBasedLibraryContext<ID extends Serializable> implements LibraryContext<ID> {

    // *********************************
    // Private Members
    // *********************************

    private final Map<ID, Artist<ID>> artistIndex = new HashMap<>();
    private final Map<ID, Album<ID>> albumIndex = new HashMap<>();
    private final Map<ID, Song<ID>> songIndex = new HashMap<>();

    private final Map<String, ID> artistReverseIndexName = new HashMap<>();
    private final Map<Artist, Map<String, ID>> albumReverseIndexArtistAndTitle = new HashMap<>();
    private final Map<Album, Map<Integer, ID>> songReverseIndexAlbumAndTrack = new HashMap<>();

    // *********************************
    // Context Methods
    // *********************************

    @Override
    public List<Artist<ID>> getArtists() {
        return Collections.unmodifiableList(new ArrayList<>(artistIndex.values()));
    }

    @Override
    public Artist<ID> getArtist(ID id) throws LibraryException {
        return findArtist(id).orElseThrow(() -> new LibraryException("Artist [" + id + "] was not found"));
    }

    @Override
    public Optional<Artist<ID>> findArtist(ID id) {
        return Optional.ofNullable(artistIndex.get(id));
    }

    @Override
    public Artist<ID> getArtistByName(String artistName) throws LibraryException {
        return findArtistByName(artistName)
                .orElseThrow(() -> new LibraryException("Artist not found"));
    }

    @Override
    public Optional<Artist<ID>> findArtistByName(String artistName) {
        ID id = artistReverseIndexName.get(artistName);
        if (Objects.isNull(id))
            return Optional.empty();

        return findArtist(id);
    }

    @Override
    public List<Album<ID>> getAlbums() {
        return Collections.unmodifiableList(new ArrayList<>(albumIndex.values()));
    }

    @Override
    public List<Album<ID>> getAlbums(Artist<ID> artist) {
        Map<String, ID> idMap = albumReverseIndexArtistAndTitle.get(artist);
        if (Objects.isNull(idMap))
            return Collections.emptyList();

        return Collections.unmodifiableList(idMap.values()
                .stream()
                .map(albumIndex::get)
                .collect(Collectors.toList()));
    }

    @Override
    public Album<ID> getAlbum(ID id) throws LibraryException {
        return findAlbum(id).orElseThrow(() -> new LibraryException("Album [" + id + "] was not found"));
    }

    @Override
    public Optional<Album<ID>> findAlbum(ID id) {
        return Optional.ofNullable(albumIndex.get(id));
    }

    @Override
    public Album<ID> getAlbumByArtistAndTitle(Artist<ID> artist, String albumName) throws LibraryException {
        return findAlbumByArtistAndTitle(artist, albumName)
                .orElseThrow(() -> new LibraryException("Album not found"));
    }

    @Override
    public Optional<Album<ID>> findAlbumByArtistAndTitle(Artist<ID> artist, String albumName) {
        Map<String, ID> albumReverseIndexTitle = albumReverseIndexArtistAndTitle.get(artist);
        if (Objects.isNull(albumReverseIndexTitle))
            return Optional.empty();

        ID id = albumReverseIndexTitle.get(albumName);
        if (Objects.isNull(id))
            return Optional.empty();

        return findAlbum(id);
    }

    @Override
    public List<Song<ID>> getSongs() {
        return Collections.unmodifiableList(new ArrayList<>(songIndex.values()));
    }

    @Override
    public List<Song<ID>> getSongsByArtist(Artist<ID> artist) {
        return null;
    }

    @Override
    public List<Song<ID>> getSongsByAlbum(Album<ID> album) {
        return null;
    }

    @Override
    public Song<ID> getSong(ID id) throws LibraryException {
        return findSong(id).orElseThrow(() -> new LibraryException("Song [" + id + "] was not found"));
    }

    @Override
    public Optional<Song<ID>> findSong(ID id) {
        return Optional.ofNullable(songIndex.get(id));
    }

    @Override
    public Song<ID> getSongByAlbumAndTrackNumber(Album<ID> album, int trackNo) throws LibraryException {
        return findSongByAlbumAndTrackNumber(album, trackNo)
                .orElseThrow(() -> new LibraryException("Song not found"));
    }

    @Override
    public Optional<Song<ID>> findSongByAlbumAndTrackNumber(Album<ID> album, int trackNo) {
        Map<Integer, ID> songReverseIndexTrack = songReverseIndexAlbumAndTrack.get(album);
        if (Objects.isNull(songReverseIndexTrack))
            return Optional.empty();

        ID id = songReverseIndexTrack.get(trackNo);
        if (Objects.isNull(id))
            return Optional.empty();

        return findSong(id);
    }

    @Override
    public Artist<ID> save(Artist<ID> artist) {
        Objects.requireNonNull(artist);
        if (isArtistTracked(artist)) {
            // Todo: do something about this
            // Todo: also test this!
        }

        saveArtistIndex(artist);
        saveArtistReverseIndex(artist);
        return artist;
    }

    @Override
    public Album<ID> save(Album<ID> album) {
        Objects.requireNonNull(album);
        if (isAlbumTracked(album)) {
            // Todo: do something about this
            // Todo: also test this!
        }

        Artist<ID> artist = album.getAlbumArtist();
        if (!isArtistTracked(artist))
            save(artist);

        saveAlbumIndex(album);
        saveAlbumReverseIndex(album);
        return album;
    }

    @Override
    public Song<ID> save(Song<ID> song) {
        Objects.requireNonNull(song);
        if (isSongTracked(song)) {
            // Todo: do something about this
            // Todo: also test this!
        }

        Artist<ID> artist = song.getArtist();
        if (!isArtistTracked(artist))
            save(artist);

        Album<ID> album = song.getAlbum();
        if (!isAlbumTracked(album))
            save(album);

        saveSongIndex(song);
        saveSongReverseIndex(song);
        return song;
    }

    // *********************************
    // Private Helper Methods
    // *********************************

    /*
     * Check if the specified Artist is already tracked.
     */
    private boolean isArtistTracked(Artist<ID> artist) {
        if (artistIndex.containsKey(artist.getId())) {
            // Todo: detect a duplicate...
        }

        if (artistReverseIndexName.containsKey(artist.getName())) {
            // Todo: detect a duplicate...
        }

        return false;
    }

    /*
     * Save the Artist into the ID-based index
     */
    private void saveArtistIndex(Artist<ID> artist) {
        artistIndex.put(artist.getId(), artist);
    }

    /*
     * Save the Artist into the ID-based index
     */
    private void saveArtistReverseIndex(Artist<ID> artist) {
        artistReverseIndexName.put(artist.getName(), artist.getId());
    }

    /*
     * Check if the specified Album is already tracked.
     */
    private boolean isAlbumTracked(Album<ID> album) {
        if (albumIndex.containsKey(album.getId())) {
            // Todo: detect a duplicate...
        }

        if (albumReverseIndexArtistAndTitle.containsKey(album.getAlbumArtist())) {
            Map<String, ID> albumReverseIndexTitle = albumReverseIndexArtistAndTitle.get(album.getAlbumArtist());
            if (albumReverseIndexTitle.containsKey(album.getTitle())) {
                // Todo: detect a duplicate...
            }
        }

        return false;
    }

    /*
     * Save the Album into the ID-based index
     */
    private void saveAlbumIndex(Album<ID> album) {
        albumIndex.put(album.getId(), album);
    }

    /*
     * Save the Album into the ID-based index
     */
    private void saveAlbumReverseIndex(Album<ID> album) {
        Map<String, ID> albumReverseIndexTitle
                = albumReverseIndexArtistAndTitle.computeIfAbsent(album.getAlbumArtist(), (x) -> new HashMap<>());
        albumReverseIndexTitle.put(album.getTitle(), album.getId());
    }

    /*
     * Check if the specified Song is already tracked.
     */
    private boolean isSongTracked(Song<ID> song) {
        if (songIndex.containsKey(song.getId())) {
            // Todo: detect a duplicate...
        }

        if (songReverseIndexAlbumAndTrack.containsKey(song.getAlbum())) {
            Map<Integer, ID> songReverseIndexTrack = songReverseIndexAlbumAndTrack.get(song.getAlbum());
            if (songReverseIndexTrack.containsKey(song.getTrackNumber())) {
                // Todo: detect a duplicate...
            }
        }

        return false;
    }

    /*
     * Save the Song into the ID-based index
     */
    private void saveSongIndex(Song<ID> song) {
        songIndex.put(song.getId(), song);
    }

    /*
     * Save the Song into the ID-based index
     */
    private void saveSongReverseIndex(Song<ID> song) {
        Map<Integer, ID> songReverseIndexTrack
                = songReverseIndexAlbumAndTrack.computeIfAbsent(song.getAlbum(), (x) -> new HashMap<>());
        songReverseIndexTrack.put(song.getTrackNumber(), song.getId());
    }

}
