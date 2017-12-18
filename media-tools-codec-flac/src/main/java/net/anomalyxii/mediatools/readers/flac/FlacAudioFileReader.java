package net.anomalyxii.mediatools.readers.flac;

import net.anomalyxii.mediatools.api.readers.AudioFileReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * An {@link AudioFileReader} for reading
 * {@link FlacAudioFile FLAC files}.
 * <p>
 * Created by Anomaly on 18/04/2016.
 */
public class FlacAudioFileReader implements AudioFileReader {

    // ******************************
    // AudioFileReader Methods
    // ******************************

    @Override
    public boolean canRead(URI uri) {
        try (InputStream in = uri.toURL().openStream()) {
            byte[] header = new byte[4];
            int count = in.read(header);
            if(count < 4)
                return false; // Todo: can we handle this better?

            return FlacMetadataReader.isHeader(header);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public FlacAudioFile read(URI uri) throws IOException {
        FlacMetadata metadata = FlacMetadataReader.getInstance().read(uri);
        return new FlacAudioFile(uri, metadata);
    }

}
