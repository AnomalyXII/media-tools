package net.anomalyxii.mediatools.api.models;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * An Audio File.
 *
 * Created by Anomaly on 18/04/2016.
 */
public interface AudioFile {

    // ******************************
    // URI Methods
    // ******************************

    /**
     * Get the source {@link URI} of this {@code AudioFile}.
     *
     * @return the source {@link URI}
     */
    URI getSource();

    // ******************************
    // Common Metadata Methods
    // ******************************

    /**
     * Get the {@code Track Number} from the {@code metadata}.
     *
     * @return the {@code Track Number} or {@literal null} if this field isn't present in the {@code metadata}
     */
    String getTrackNumber();

    /**
     * Get the {@code Title} from the {@code metadata}.
     *
     * @return the {@code Title} or {@literal null} if this field isn't present in the {@code metadata}
     */
    String getTitle();

    /**
     * Get the {@code Album Name} from the {@code metadata}.
     *
     * @return the {@code Album Name} or {@literal null} if this field isn't present in the {@code metadata}
     */
    String getAlbumName();

    /**
     * Get the {@code Artist Name} from the {@code metadata}.
     *
     * @return the {@code Artist Name} or {@literal null} if this field isn't present in the {@code metadata}
     */
    String getArtistName();


    /**
     * Get the {@code Album Artist Name} from the {@code metadata}.
     *
     * @return the {@code Album Artist Name} or {@literal null} if this field isn't present in the {@code metadata}
     */
    String getAlbumArtistName();

    // ******************************
    // AudioStream Methods
    // ******************************

    /**
     * Open a new {@link InputStream} that can be used to read this
     * {@code Audio File}.
     *
     * @return a new {@link InputStream}
     * @throws IOException if anything goes wrong opening the stream
     * @see javax.sound.sampled.AudioInputStream
     */
    InputStream openAudioStream() throws IOException;

}
