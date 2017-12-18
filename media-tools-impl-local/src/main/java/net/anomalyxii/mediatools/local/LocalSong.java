package net.anomalyxii.mediatools.local;

import net.anomalyxii.mediatools.api.models.Album;
import net.anomalyxii.mediatools.api.models.Artist;
import net.anomalyxii.mediatools.api.models.AudioFile;
import net.anomalyxii.mediatools.api.models.Song;

import java.nio.file.Path;

/**
 * Created by Anomaly on 15/04/2016.
 */
public class LocalSong implements Song<Long> {

    // ******************************
    // Members
    // ******************************

    private final Long id;
    private final Path file;
    private final AudioFile audioFile;
    private final LocalArtist artist;
    private final LocalAlbum album;

    // ******************************
    // Constructors
    // ******************************

    public LocalSong(long id, Path file, AudioFile audioFile, LocalArtist artist, LocalAlbum album) {
        this.id = id;
        this.file = file;
        this.audioFile = audioFile;
        this.artist = artist;
        this.album = album;
    }

    // ******************************
    // Getters
    // ******************************

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public int getTrackNumber() {
        try {
            return Integer.parseInt(audioFile.getTrackNumber());
        } catch(Exception e) {
            return -1;
        }
    }

    @Override
    public String getTitle() {
        return audioFile.getTitle();
    }

    @Override
    public LocalArtist getArtist() {
        return artist;
    }

    @Override
    public LocalAlbum getAlbum() {
        return album;
    }

    // ******************************
    // Helpers
    // ******************************

    public Path toPath() {
        return file;
    }

}
