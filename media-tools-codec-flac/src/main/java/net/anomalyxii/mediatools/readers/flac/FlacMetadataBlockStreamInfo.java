package net.anomalyxii.mediatools.readers.flac;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Anomaly on 17/04/2016.
 */
class FlacMetadataBlockStreamInfo extends FlacMetadataBlock {

    // ******************************
    // Members
    // ******************************

    private int minBlockSize;
    private int maxBlockSize;
    private int minFrameSize;
    private int maxFrameSize;
    private int sampleRate;
    private short noOfChannels;
    private short bitsPerSample;
    private int totalSamplesInStream;
    private String md5Signature;

    // ******************************
    // Constructors
    // ******************************

    private FlacMetadataBlockStreamInfo(boolean last, int length) {
        super(last, STREAMINFO, length);
    }

    // ******************************
    // Getters
    // ******************************

    int getMinimumBlockSize() {
        return minBlockSize;
    }

    int getMaximumBlockSize() {
        return maxBlockSize;
    }

    int getMinimumFrameSize() {
        return minFrameSize;
    }

    int getMaximumFrameSize() {
        return maxFrameSize;
    }

    int getSampleRate() {
        return sampleRate;
    }

    short getNumberOfChannels() {
        return noOfChannels;
    }

    short getBitsPerSample() {
        return bitsPerSample;
    }

    int getTotalSamplesInStream() {
        return totalSamplesInStream;
    }

    // ******************************
    // Static Read Method
    // ******************************

    static FlacMetadataBlockStreamInfo read(boolean last, int length, InputStream in) throws IOException {
        FlacMetadataBlockStreamInfo block = new FlacMetadataBlockStreamInfo(last, length);
        if (length != 34)
            throw new IOException("Could not read fLaC STREAMINFO - expected length=34, actual length=" + length);

        byte[] buffer = new byte[length];
        int read = in.read(buffer);
        if (read < length)
            throw new IOException("Could not read fLaC STREAMINFO - unexpected EOF");

        block.minBlockSize = (0x00FF & buffer[0]) << 8 | (0x00FF & buffer[1]);
        block.maxBlockSize = (0x00FF & buffer[2]) << 8 | (0x00FF & buffer[3]);
        block.minFrameSize = ((((0x00FF & buffer[4]) << 8) | (0x00FF & buffer[5])) << 8) | (0x00FF & buffer[6]);
        block.maxFrameSize = ((((0x00FF & buffer[7]) << 8) | (0x00FF & buffer[8])) << 8) | (0x00FF & buffer[9]);
        block.sampleRate = ((((0x00FF & buffer[10]) << 8) | (0x00FF & buffer[11])) << 4) | ((0x00F0 & buffer[12]) >> 4);
        block.noOfChannels = (short) (((0x000E & buffer[12]) >> 1) + 1);
        block.bitsPerSample = (short) ((((0x0001 & buffer[12]) << 4) | (0x00F0 & buffer[13]) >> 4) + 1);
        block.totalSamplesInStream = ((((((0x000F & buffer[13]) << 8)
                                        | (0x00FF & buffer[14])) << 8
                                        | (0x00FF & buffer[15])) << 8
                                        | (0x00FF & buffer[16])) << 8
                                        | (0x00FF & buffer[17]));

        StringBuilder hashBuilder = new StringBuilder();
        for(int i = 0; i < 16; i++)
            hashBuilder.append(String.format("%02x", (0x00FF & buffer[18 + i])));
        block.md5Signature = hashBuilder.toString();

        return block;
    }

}
