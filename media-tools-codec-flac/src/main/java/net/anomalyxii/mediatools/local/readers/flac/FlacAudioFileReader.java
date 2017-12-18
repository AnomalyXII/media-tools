package net.anomalyxii.mediatools.local.readers.flac;

import net.anomalyxii.mediatools.api.readers.AudioFileReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by Anomaly on 18/04/2016.
 */
public class FlacAudioFileReader implements AudioFileReader {

    // ******************************
    // Members
    // ******************************

    // ******************************
    // Constructors
    // ******************************


    // ******************************
    // AudioFileReader Methods
    // ******************************

    @Override
    public boolean canRead(URI uri) {
        try(InputStream in = uri.toURL().openStream()) {
            byte[] header = new byte[4];
            in.read(header);

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
