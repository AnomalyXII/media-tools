package net.anomalyxii.mediatools.local;

import net.anomalyxii.mediatools.api.builders.AbstractLibraryBuilder;
import net.anomalyxii.mediatools.api.models.*;
import net.anomalyxii.mediatools.api.readers.AudioFileReader;
import net.anomalyxii.mediatools.api.readers.AudioFileReaders;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Load a music {@link Library} by scanning the local file
 * system to detect {@link Song songs} and then deriving
 * further information from the ID3 tags (or similar meta
 * information)
 * <p>
 * Created by Anomaly on 15/04/2016.
 */
public class LocalLibraryBuilder extends AbstractLibraryBuilder<Long> {

    // ******************************
    // Members
    // ******************************

    private final Map<Long, LocalSong> songIndex = new HashMap<>();
    private final Map<Path, Long> songReverseIndexPath = new HashMap<>();
    //
    private final Map<Long, LocalArtist> artistIndex = new HashMap<>();
    private final Map<String, Long> artistReverseIndexName = new HashMap<>();
    private final Map<Long, LocalAlbum> albumIndex = new HashMap<>();
    private final Map<LocalArtist, Map<String, Long>> albumReverseIndexArtistAndName = new HashMap<>();

    // ******************************
    // Constructors
    // ******************************

    public LocalLibraryBuilder() {
        this(LocalLibrary::new);
    }

    private LocalLibraryBuilder(Supplier<MutableLibrary<Long>> factory) {
        super(factory);
    }

    // ******************************
    // AbstractLibraryBuilder Methods
    // ******************************

    @Override
    public Artist<Long> find(String artistName) {
        if (!artistReverseIndexName.containsKey(artistName)) {
            return null;
        }

        Long id = artistReverseIndexName.get(artistName);
        if (id == null)
            return null;

        return artistIndex.get(id);

    }

    @Override
    public Album<Long> find(String albumName, Artist<Long> artist) {
        Map<String, Long> albumReverseIndexName
                = albumReverseIndexArtistAndName.computeIfAbsent(artist, k -> new HashMap<>());

        if (!albumReverseIndexName.containsKey(albumName))
            return null;

        Long id = albumReverseIndexName.get(albumName);
        if (id == null)
            return null;

        return albumIndex.get(id);
    }

    @Override
    public Song<Long> find(int trackNo, Album<Long> album) {
        return null;
    }

    @Override
    public Artist<Long> create(String artistName) {
        return null;
    }

    @Override
    public Album<Long> create(String albumName, Artist<Long> artist) {
        return null;
    }

    @Override
    public Song<Long> create(int trackNo, String title, Artist<Long> albumArtist, Album<Long> album) {
        return null;
    }

    @Override
    public Artist<Long> save(Artist<Long> artist) {
        return null;
    }

    @Override
    public Album<Long> save(Album<Long> album) {
        return null;
    }

    @Override
    public Song<Long> save(Song<Long> song) {
        return null;
    }

    public LocalArtist findOrCreateArtist(String artistName) {


        synchronized (artistIndex) {
            long nextId = artistIndex.keySet().stream().max(Long::compare).orElse(0L);
            artistReverseIndexName.put(artistName, ++nextId);

            LocalArtist artist = new LocalArtist(nextId, artistName);
            artistIndex.put(nextId, artist);
            return artist;
        }
    }

    public LocalAlbum findOrCreateAlbum(LocalArtist artist, String albumName) {

        synchronized (albumIndex) {
            long nextId = albumIndex.keySet().stream().max(Long::compare).orElse(0L);
            albumReverseIndexName.put(albumName, ++nextId);

            LocalAlbum album = new LocalAlbum(nextId, albumName, artist);
            albumIndex.put(nextId, album);
            return album;
        }
    }

    public LocalSong findOrCreateSong(Path file, AudioFile audioFile, LocalArtist artist, LocalAlbum album) {
        if (songReverseIndexPath.containsKey(file)) {
            long id = songReverseIndexPath.get(file);
            return songIndex.get(id);
        }

        long id = songIndex.keySet().stream().max(Long::compare).orElse(0L);
        songReverseIndexPath.put(file, ++id);

        // Todo: does this belong here?
        AudioFileReader reader = AudioFileReaders.getReader(file.toUri());
        if (reader == null)
            throw new AssertionError("Could not read song: " + file);

        synchronized (songIndex) {
            LocalSong song = new LocalSong(id, file, audioFile, artist, album);
            System.out.println("Added song: " + file);
            songIndex.put(id, song);
            return song;
        }
    }


}
