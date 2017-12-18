package net.anomalyxii.mediatools.readers.flac;

import net.anomalyxii.mediatools.api.AudioFile;

import java.io.IOException;
import java.net.URI;

/**
 * An {@link AudioFile} that corresponds to a FLAC file.
 * <p>
 * Created by Anomaly on 18/04/2016.
 */
public class FlacAudioFile implements AudioFile {

    // ******************************
    // Members
    // ******************************

    private final URI uri;
    private final FlacMetadata metadata;

    // ******************************
    // Constructors
    // ******************************

    public FlacAudioFile(URI uri, FlacMetadata metadata) {
        this.uri = uri;
        this.metadata = metadata;
    }

    // ******************************
    // AudioFile Methods
    // ******************************

    @Override
    public URI getSource() {
        return uri;
    }

    @Override
    public String getTrackNumber() {
        return metadata.get(FlacMetadata.TRACKNUMBER);
    }

    @Override
    public String getTitle() {
        return metadata.get(FlacMetadata.TITLE);
    }

    @Override
    public String getAlbumName() {
        return metadata.get(FlacMetadata.ALBUM);
    }

    @Override
    public String getArtistName() {
        return metadata.get(FlacMetadata.ARTIST);
    }

    @Override
    public String getAlbumArtistName() {
        return metadata.get(FlacMetadata.ALBUMARTIST);
    }

    @Override
    public FlacInputStream openAudioStream() throws IOException {
        return new FlacInputStream(uri.toURL().openStream());
    }

    // ******************************
    // Getters
    // ******************************

    /**
     * Get the {@link FlacMetadata} from this {@link AudioFile}.
     *
     * @return the {@link FlacMetadata}
     */
    public FlacMetadata getMetadata() {
        return metadata;
    }

}
