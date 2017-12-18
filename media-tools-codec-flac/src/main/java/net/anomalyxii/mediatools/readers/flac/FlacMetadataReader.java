package net.anomalyxii.mediatools.readers.flac;

import net.anomalyxii.mediatools.api.readers.MetadataReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link MetadataReader} for reading from a FLAC file.
 * <p>
 * Created by Anomaly on 17/04/2016.
 */
public class FlacMetadataReader implements MetadataReader {

    private static final FlacMetadataReader INSTANCE = new FlacMetadataReader();

    // ******************************
    // Constructors
    // ******************************

    private FlacMetadataReader() {
    }

    // ******************************
    // MetadataReader Methods
    // ******************************

    @Override
    public FlacMetadata read(URI uri) throws IOException {
        try (InputStream in = uri.toURL().openStream()) {
            return read(in);
        }
    }

    // ******************************
    // MetadataReader Methods
    // ******************************

    /**
     * Read the FLAC {@link InputStream}.
     *
     * @param in the {@link InputStream}
     * @return the {@link FlacMetadata}
     * @throws IOException if something goes wrong reading the metadata
     */
    protected FlacMetadata read(InputStream in) throws IOException {

        byte[] blockHeader = new byte[4];
        int read = in.read(blockHeader);
        if (read == -1)
            throw new FlacReaderException("Could not read fLaC header - unexpected EOF");

        if (!isHeader(blockHeader))
            throw new FlacReaderException("Could not read fLaC header");

        FlacMetadataBlock streamInfo = readMetadataBlock(in);
        List<FlacMetadataBlock> otherBlocks = new ArrayList<>();

        FlacMetadataBlock lastBlock = streamInfo;
        while (!lastBlock.isLastBlock())
            otherBlocks.add(lastBlock = readMetadataBlock(in));

        return FlacMetadata.build((FlacMetadataBlockStreamInfo) streamInfo, otherBlocks);

    }

    // ******************************
    // Static Helper Methods
    // ******************************

    /**
     * Check if the first four {@literal bytes} provided correspond to the
     * FLAC header ({@code fLaC}).
     *
     * @param header an array of {@literal bytes}
     * @return {@literal true} if the array is at least four bytes long and starts with {@code fLaC}; {@literal false} otherwise
     */
    protected static boolean isHeader(byte[] header) {
        return header.length >= 4 && header[0] == 0x66 && header[1] == 0x4C && header[2] == 0x61 && header[3] == 0x43;
    }

    /**
     * Read a single {@link FlacMetadataBlock} from the {@link InputStream}.
     *
     * @param in the {@link InputStream}
     * @return the next {@link FlacMetadataBlock} in the stream
     * @throws IOException if something goes wrong reading the metadata block
     */
    protected static FlacMetadataBlock readMetadataBlock(InputStream in) throws IOException {

        int read;

        byte[] header = new byte[4];
        read = in.read(header);
        if (read == -1)
            throw new FlacReaderException("Could not read fLaC METADATA header - unexpected EOF");

        boolean isLast = (0x0080 & header[0]) == 0x0080;
        int blockType = (0x007F & header[0]);
        int length = ((((0x00FF & header[1]) << 8) | (0x00FF & header[2])) << 8) | (0x00FF & header[3]);

        switch (blockType) {

            case FlacMetadataBlock.STREAMINFO:
                return FlacMetadataBlockStreamInfo.read(isLast, length, in);

            case FlacMetadataBlock.PADDING:
                return FlacMetadataBlockPadding.read(isLast, length, in);

            case FlacMetadataBlock.APPLICATION:
                return FlacMetadataBlockApplication.read(isLast, length, in);

            case FlacMetadataBlock.SEEKTABLE:
                return FlacMetadataBlockSeektable.read(isLast, length, in);

            case FlacMetadataBlock.VORBIS_COMMENT:
                return FlacMetadataBlockVorbisComment.read(isLast, length, in);

            default:
                throw new UnsupportedOperationException("Cannot read block type: " + blockType);

        }

    }

    // ******************************
    // Singleton Methods
    // ******************************

    /**
     * Get the singleton {@code FlacMetadataReader} instance.
     *
     * @return the {@link FlacMetadataReader} instance
     */
    public static FlacMetadataReader getInstance() {
        return INSTANCE;
    }

}
