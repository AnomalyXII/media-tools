package net.anomalyxii.mediatools.local.readers.flac;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Anomaly on 17/04/2016.
 */
class FlacMetadataBlockApplication extends FlacMetadataBlock {

    // ******************************
    // Members
    // ******************************

    private String application;
    private byte[] data;

    // ******************************
    // Constructors
    // ******************************

    private FlacMetadataBlockApplication(boolean last, int length) {
        super(last, APPLICATION, length);
    }

    // ******************************
    // Static Read Method
    // ******************************

    static FlacMetadataBlockApplication read(boolean last, int length, InputStream in) throws IOException {
        FlacMetadataBlockApplication block = new FlacMetadataBlockApplication(last, length);

        byte[] buffer = new byte[length];
        int read = in.read(buffer);
        if(read < length)
            throw new IOException("Could not read fLaC APPLICATION - unexpected EOF");

        block.application = new String(buffer, 0, 4);
        block.data = new byte[length - 4];
        System.arraycopy(buffer, 4, block.data, 0, block.data.length);

        return block;
    }

}
