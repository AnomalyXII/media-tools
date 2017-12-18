package net.anomalyxii.mediatools.local.readers.mp3;

import net.anomalyxii.mediatools.api.AudioFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by Anomaly on 21/04/2016.
 */
public class Mp3AudioFile implements AudioFile {

    // ******************************
    // Members
    // ******************************

    private final Mp3Metadata metadata;
    private final URI source;

    // ******************************
    // Constructors
    // ******************************

    public Mp3AudioFile(Mp3Metadata metadata, URI source) {
        this.metadata = metadata;
        this.source = source;
    }

    // ******************************
    // AudioFile Methods
    // ******************************

    @Override
    public URI getSource() {
        return source;
    }

    @Override
    public String getTrackNumber() {
        return metadata.get(FrameType.TRCK);
    }

    @Override
    public String getTitle() {
        return metadata.get(FrameType.TIT2);
    }

    @Override
    public String getAlbumName() {
        return metadata.get(FrameType.TALB);
    }

    @Override
    public String getArtistName() {
        return metadata.get(FrameType.TPE1);
    }

    @Override
    public String getAlbumArtistName() {
        return metadata.get(FrameType.TPE2);
    }

    @Override
    public InputStream openAudioStream() throws IOException {
        return source.toURL().openStream();
    }

}
