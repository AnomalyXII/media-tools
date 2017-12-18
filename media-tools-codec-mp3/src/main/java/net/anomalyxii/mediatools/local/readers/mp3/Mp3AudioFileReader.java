package net.anomalyxii.mediatools.local.readers.mp3;

import net.anomalyxii.mediatools.api.models.AudioFile;
import net.anomalyxii.mediatools.api.models.Metadata;
import net.anomalyxii.mediatools.api.readers.AudioFileReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by Anomaly on 21/04/2016.
 */
public class Mp3AudioFileReader implements AudioFileReader {

    // ******************************
    // Members
    // ******************************

    // ******************************
    // Constructors
    // ******************************

    // ******************************
    // MetadataReader Methods
    // ******************************

    @Override
    public boolean canRead(URI uri) {
        try (InputStream in = uri.toURL().openStream()) {
            // Todo: support ID3v2 being at the end of a file??
            byte[] header = new byte[10];
            in.read(header);

            return determineMetadataVersion(header) != null;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public AudioFile read(URI uri) throws IOException {
        Mp3Metadata metadata = Id3v2MetadataReader.getInstance().read(uri);
        return new Mp3AudioFile(metadata, uri);
    }

    // ******************************
    // Helper Methods
    // ******************************

    protected static MetadataVersion determineMetadataVersion(byte[] header) throws IOException {

        if (header.length < 5)
            return null;

        if (header[0] == 'I' && header[1] == 'D' && header[2] == '3') {
            if (header[3] == 0x03)
                return MetadataVersion.Id3v2_0300;

            if (header[3] == 0x04)
                return MetadataVersion.Id3v2_0400;

            throw new IOException("Unsupported ID3v2 format: " + header[3]);
        }

        if (header[0] == 'A' && header[1] == 'P' && header[2] == 'E' && header[3] == 'T'
            && header[4] == 'A' && header[5] == 'G' && header[6] == 'E' && header[7] == 'X')
            return MetadataVersion.APE2;

        return null;

    }

    // ******************************
    // Metadata Version
    // ******************************

    enum MetadataVersion {

        Id3v2_0300,
        Id3v2_0400,
        APE2

    }

}
