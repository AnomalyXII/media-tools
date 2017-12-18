package net.anomalyxii.mediatools.local.readers.flac;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Anomaly on 17/04/2016.
 */
class FlacMetadataBlockPadding extends FlacMetadataBlock {

    // ******************************
    // Members
    // ******************************

    // ******************************
    // Constructors
    // ******************************

    private FlacMetadataBlockPadding(boolean last, int length) {
        super(last, PADDING, length);
    }

    // ******************************
    // Static Read Method
    // ******************************

    static FlacMetadataBlockPadding read(boolean last, int length, InputStream in) throws IOException {
        FlacMetadataBlockPadding block = new FlacMetadataBlockPadding(last, length);
        while(length > 0) {
            long skipped = in.skip(length);
            length -= skipped;
        }
        return block;
    }

}
