package net.anomalyxii.mediatools.local.readers.flac;

/**
 * Created by Anomaly on 17/04/2016.
 */
class FlacMetadataBlock {

    final static int STREAMINFO = 0;
    final static int PADDING = 1;
    final static int APPLICATION = 2;
    final static int SEEKTABLE = 3;
    final static int VORBIS_COMMENT = 4;
    final static int CUESHEET = 5;
    final static int PICTURE = 6;

    // ******************************
    // Members
    // ******************************

    // Header
    private final boolean last;
    private final int type;
    private final int length;

    // ******************************
    // Constructors
    // ******************************

    public FlacMetadataBlock(boolean last, int type, int length) {
        this.last = last;
        this.type = type;
        this.length = length;
    }

    // ******************************
    // Getters
    // ******************************

    public boolean isLastBlock() {
        return last;
    }

    public int getBlockType() {
        return type;
    }

    public int getBlockLength() {
        return length;
    }

}
