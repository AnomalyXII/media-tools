package net.anomalyxii.mediatools.api.readers;

import net.anomalyxii.mediatools.api.AudioFile;

import java.io.IOException;
import java.net.URI;

/**
 * An interface for reading {@link AudioFile}
 *
 * Created by Anomaly on 18/04/2016.
 */
public interface AudioFileReader {

    // *********************************
    // Interface Methods
    // *********************************

    boolean canRead(URI uri);

    AudioFile read(URI uri) throws IOException;


}
