package net.anomalyxii.mediatools.api.readers;

import java.net.URI;
import java.util.ServiceLoader;

/**
 * A ServiceLoader for the {@link AudioFileReader} interface.
 *
 * Created by Anomaly on 18/04/2016.
 */
public final class AudioFileReaders {

    private static ServiceLoader<AudioFileReader> readers = ServiceLoader.load(AudioFileReader.class);

    // ******************************
    // Constructors
    // ******************************

    private AudioFileReaders() {
    } // Do not instantiate me, please!

    // ******************************
    // Get Reader
    // ******************************


    /**
     * Get an {@link AudioFileReader} that can read from the
     * specified {@link URI}; if multiple readers can read the URI
     * the exact reader that will be returned is undefined.
     *
     * @param uri the {@link URI} to read
     * @return the {@link AudioFileReader} that can read the {@link URI}, or {@literal null} if no reader is available
     */
    public static AudioFileReader getReader(URI uri) {

        for (AudioFileReader reader : readers) {
            if (reader.canRead(uri))
                return reader;
        }

        return null;

    }


}
