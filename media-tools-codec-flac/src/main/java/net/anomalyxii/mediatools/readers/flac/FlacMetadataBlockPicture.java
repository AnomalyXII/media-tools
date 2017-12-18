package net.anomalyxii.mediatools.readers.flac;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Anomaly on 17/04/2016.
 */
class FlacMetadataBlockPicture extends FlacMetadataBlock {

    // ******************************
    // Members
    // ******************************

    // ******************************
    // Constructors
    // ******************************

    private FlacMetadataBlockPicture(boolean last, int length) {
        super(last, PADDING, length);
    }

    // ******************************
    // Static Read Method
    // ******************************

    static FlacMetadataBlockPicture read(boolean last, int length, InputStream in) throws IOException {
        FlacMetadataBlockPicture block = new FlacMetadataBlockPicture(last, length);
        while(length > 0) {
            long skipped = in.skip(length);
            length -= skipped;
        }
        return block;
    }

}
