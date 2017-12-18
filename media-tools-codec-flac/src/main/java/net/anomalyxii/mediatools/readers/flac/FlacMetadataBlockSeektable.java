package net.anomalyxii.mediatools.readers.flac;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Anomaly on 17/04/2016.
 */
class FlacMetadataBlockSeektable extends FlacMetadataBlock {

    // ******************************
    // Members
    // ******************************

    private final List<Seekpoint> seekpoints = new ArrayList<>();

    // ******************************
    // Constructors
    // ******************************

    private FlacMetadataBlockSeektable(boolean last, int length) {
        super(last, SEEKTABLE, length);
    }

    // ******************************
    // Getters
    // ******************************

    public List<Seekpoint> getSeekpoints() {
        return Collections.unmodifiableList(seekpoints);
    }

    // ******************************
    // Seekpoint Class
    // ******************************

    static class Seekpoint {

        int targetFrame;
        int tragetFrameOffset;
        int noOfSamples;

    }

    // ******************************
    // Static Read Method
    // ******************************

    static FlacMetadataBlockSeektable read(boolean last, int length, InputStream in) throws IOException {
        FlacMetadataBlockSeektable block = new FlacMetadataBlockSeektable(last, length);
        if (length % 18 != 0)
            throw new IOException("Could not read fLaC SEEKTABLE - length was not a multiple of 18 (" + length + ")");

        byte[] buffer = new byte[length];
        int read = in.read(buffer);
        if (read < length)
            throw new IOException("Could not read fLaC SEEKTABLE - unexpected EOF");

        for (int l = 0; l < length; l += 18) {
            byte[] seekpointBuffer = new byte[18];
            System.arraycopy(buffer, l, seekpointBuffer, 0, 18);

            Seekpoint seekpoint = new Seekpoint();
            seekpoint.targetFrame = 0;
            for (int i = 0; i < 8; i++)
                seekpoint.targetFrame = (seekpoint.targetFrame << 8) | (0x00FF & seekpointBuffer[i]);

            seekpoint.tragetFrameOffset = 0;
            for (int i = 0; i < 8; i++)
                seekpoint.tragetFrameOffset = (seekpoint.tragetFrameOffset << 8) | (0x00FF & seekpointBuffer[8 + i]);

            seekpoint.noOfSamples = ((0x00FF & seekpointBuffer[16]) << 8) | seekpointBuffer[17];
            block.seekpoints.add(seekpoint);
        }

        return block;
    }

}
