package net.anomalyxii.mediatools.plex.builders;

import net.anomalyxii.mediatools.api.AudioFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Objects;

/**
 * An {@link AudioFile} for media hosted on Plex.
 */
public class PlexAudioFile implements AudioFile {

    // *********************************
    // Members
    // *********************************

    private final URI source;
    private final String trackNumber;
    private final String title;
    private final String albumName;
    private final String artistName;
    private final String albumArtistName;

    // *********************************
    // Constructors
    // *********************************

    public PlexAudioFile(URI source, String trackNumber, String title,
                         String albumName, String artistName, String albumArtistName) {
        this.source = Objects.requireNonNull(source);
        this.trackNumber = Objects.requireNonNull(trackNumber);
        this.title = Objects.requireNonNull(title);
        this.albumName = Objects.requireNonNull(albumName);
        this.artistName = Objects.requireNonNull(artistName);
        this.albumArtistName = Objects.requireNonNull(albumArtistName);
    }

    // *********************************
    // AudioFile Methods
    // *********************************

    @Override
    public URI getSource() {
        return source;
    }

    @Override
    public String getTrackNumber() {
        return trackNumber;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAlbumName() {
        return albumName;
    }

    @Override
    public String getArtistName() {
        return artistName;
    }

    @Override
    public String getAlbumArtistName() {
        return albumArtistName;
    }

    @Override
    public InputStream openAudioStream() throws IOException {
        return null;
    }

    // *********************************
    // To String
    // *********************************

    @Override
    public String toString() {
        return String.format("PlexAudioFile[%s - %s]", trackNumber, title);
    }

}
