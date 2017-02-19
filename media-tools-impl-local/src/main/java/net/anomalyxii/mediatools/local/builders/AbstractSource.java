package net.anomalyxii.mediatools.local.builders;

import net.anomalyxii.mediatools.api.AudioFile;
import net.anomalyxii.mediatools.api.builders.Source;
import net.anomalyxii.mediatools.api.exceptions.MediaException;
import net.anomalyxii.mediatools.api.readers.AudioFileReader;
import net.anomalyxii.mediatools.api.readers.AudioFileReaders;

import java.io.IOException;
import java.net.URI;

/**
 * Abstract implementation of the {@link Source} interface
 * that will build an {@link AudioFile} from the
 * {@code Source Source's} {@link URI}.
 *
 * Created by Anomaly on 19/02/2017.
 */
public abstract class AbstractSource implements Source {

    // ******************************
    // Members
    // ******************************

    // ******************************
    // Source Methods
    // ******************************

    @Override
    public AudioFile toAudioFile() throws MediaException {
        if(!isLeaf())
            throw new MediaException("Cannot convert a non-leaf Source to an AudioFile");

        URI uri = toUri();
        AudioFileReader reader = AudioFileReaders.getReader(uri);
        if (reader == null)
            throw new MediaException("No suitable reader found for '" + uri + "'");

        try {
            return reader.read(uri);
        } catch (IOException e) {
            throw new MediaException(e);
        }
    }

    // ******************************
    // To String
    // ******************************


    @Override
    public String toString() {
        return "Source { " + toUri() + "}";
    }

}
