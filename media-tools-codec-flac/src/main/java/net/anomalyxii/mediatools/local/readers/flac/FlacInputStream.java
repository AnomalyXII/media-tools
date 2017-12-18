package net.anomalyxii.mediatools.local.readers.flac;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Anomaly on 29/04/2016.
 */
public class FlacInputStream extends InputStream {

    // ******************************
    // Members
    // ******************************

    private final InputStream encoded;
    private final FlacMetadata metadata;
    //
    private FrameHeader frameHeader;

    // ******************************
    // Constructors
    // ******************************

    public FlacInputStream(InputStream encoded) throws IOException {
        this.encoded = encoded;
        this.metadata = FlacMetadataReader.getInstance().read(encoded);
    }


    // ******************************
    // InputStream Methods
    // ******************************

    @Override
    public int read() throws IOException {
        if (frameHeader == null)
            frameHeader = readFrameHeader(metadata, encoded);

        return 0;
    }


    // ******************************
    // Helper Methods
    // ******************************

    protected static FrameHeader readFrameHeader(FlacMetadata metadata, InputStream encoded) throws IOException {

        byte[] header = new byte[4];
        int read = encoded.read(header);
        if (read == -1)
            throw new IOException("Could not read Flac FrameHeader - Unexpected EOF");

        if (!((header[0] & 0xFF) == 0xFF && (header[1] & 0xFC) == 0xF8))
            throw new IOException("Could not read Flac FrameHeader - Expected a sync code (0xFFF8)");

        // Ignore the reserved bit...

        boolean bs = (header[1] & 0x01) == 0x01;

        byte bsics = (byte) ((header[2] & 0xF0) >> 4);
        byte sr = (byte) (header[2] & 0x0F);
        byte ca = (byte) ((header[3] & 0xF0) >> 4);
        byte ssib = (byte) ((header[3] & 0xE) >> 1);
        // Ignore the final reserved bits...

        // 	if(variable blocksize)
        //     <8-56>:"UTF-8" coded sample number (decoded number is 36 bits)
        //  else
        //     <8-48>:"UTF-8" coded frame number (decoded number is 31 bits) [4]
        int codedFrameOrSampleNumber = decodeUTF8Number(encoded);

        long bsicsDecoded = decodeBlockSize(bsics, encoded);
        long srDecoded = decodeSampleRate(metadata, sr, encoded);

        byte crc = (byte) (encoded.read() & 0xFF);

        long moo = 0;
        int curr;
        byte prev = 0;
        while ((curr = encoded.read()) != -1) {

            if (prev == -1) {
                if (((byte) curr) == -8) {
                    System.out.printf("Next sync code after %d bytes %n", moo);
                    break;
                }
            }

            prev = (byte) curr;
            moo++;
        }

        return new FrameHeader(bs, bsicsDecoded, srDecoded, ca, ssib, null, crc);

    }

    protected static int decodeUTF8Number(InputStream encoded) throws IOException {

        int value = encoded.read();
        int top = (value & 128) >> 1;

        if ((value & 0xc0) == 0x80)
            throw new IOException("Could not read Flac UTF-8 Number - Invalid UTF-8 Header");

        while ((value & top) != 0) {
            int tmp = encoded.read();
            if(tmp == -1)
                throw new IOException("Could not read Flac UTF-8 Number - Unexpected EOF");
            tmp -= 128;

            if((tmp >> 6) > 0)
                throw new IOException("Could not read Flac UTF-8 Number - Invalid UTF-8 Header");

            value = (value << 6) + tmp;
            top <<= 5;
        }

        value &= (top << 1) - 1;
        return value;

    }

    protected static long decodeBlockSize(byte bsics, InputStream encoded) throws IOException {
        switch (bsics) {
            case 0:
                throw new IOException("Could not read Flac FrameHeader - BlockSize = 0 is reserved");

            case 1:
                return 192;

            case 2:
            case 3:
            case 4:
            case 5:
                return 576 * (1 << (bsics - 2));

            case 6:
                return encoded.read();

            case 7:
                long decoded;
                decoded = encoded.read();
                decoded <<= 8;
                decoded |= encoded.read();
                return decoded;

            // Otherwise...
            default:
                return 256 * (1 << (bsics - 8));

        }
    }

    private static long decodeSampleRate(FlacMetadata metadata, byte sr, InputStream encoded) throws IOException {
        switch (sr) {
            case 0:
                return metadata.getStreamInfoBlock().getSampleRate();

            case 1:
                return 88200;
            case 2:
                return 176400;
            case 3:
                return 192000;
            case 4:
                return 8000;
            case 5:
                return 16000;
            case 6:
                return 22050;
            case 7:
                return 24000;
            case 8:
                return 32000;
            case 9:
                return 44100;
            case 10:
                return 48000;
            case 11:
                return 96000;
            case 12:
                return encoded.read() * 1000;
            case 13:
            case 14:
                long decoded;
                decoded = encoded.read();
                decoded <<= 8;
                decoded |= encoded.read();
                return sr == 13 ? decoded : decoded * 10;

            default:
                throw new IOException("Could not read Flac FrameHeader - SampleRate = 16 is reserved");

        }
    }

    protected static byte calculateCRC8(byte current, byte update) {
        return 0;
    }

    // ******************************
    // Helper Classes
    // ******************************

    protected static class FrameHeader {

        // Members

        public final boolean variableBlockingStrategy;
        public final long blockSizeInInterChannelSamples;
        public final long sampleRate;
        public final byte channelAssignment;
        public final byte sampleSizeInBits;
        //
        public final byte[] stuff;
        //
        public final byte crc;

        // Constructors

        public FrameHeader(boolean variableBlockingStrategy, long blockSizeInInterChannelSamples, long sampleRate,
                           byte channelAssignment, byte sampleSizeInBits, byte[] stuff, byte crc) {

            this.variableBlockingStrategy = variableBlockingStrategy;
            this.blockSizeInInterChannelSamples = blockSizeInInterChannelSamples;
            this.sampleRate = sampleRate;
            this.channelAssignment = channelAssignment;
            this.sampleSizeInBits = sampleSizeInBits;
            this.stuff = stuff;
            this.crc = crc;

        }
    }


}
