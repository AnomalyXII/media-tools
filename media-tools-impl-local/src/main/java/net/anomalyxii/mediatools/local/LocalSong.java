package net.anomalyxii.mediatools.local;

import net.anomalyxii.mediatools.api.AudioFile;
import net.anomalyxii.mediatools.api.Song;

import java.net.URI;

/**
 *
 *
 * Created by Anomaly on 15/04/2016.
 */
public class LocalSong implements Song<Long> {

    // ******************************
    // Members
    // ******************************

    private final Long id;
    private final Integer trackNumber;
    private final String title;
    private final LocalArtist artist;
    private final LocalAlbum album;
    private final AudioFile audioFile;

    // ******************************
    // Constructors
    // ******************************

    public LocalSong(Long id, Integer trackNumber, String title,
                     LocalArtist artist, LocalAlbum album, AudioFile audioFile) {
        this.id = id;
        this.trackNumber = trackNumber;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.audioFile = audioFile;
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
        return trackNumber;
    }

    @Override
    public String getTitle() {
        return title;
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

    public URI toURI() {
        return audioFile.getSource();
    }

}
