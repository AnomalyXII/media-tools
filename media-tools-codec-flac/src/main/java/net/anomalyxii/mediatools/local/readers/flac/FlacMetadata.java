package net.anomalyxii.mediatools.local.readers.flac;

import net.anomalyxii.mediatools.api.models.Metadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Anomaly on 17/04/2016.
 */
public class FlacMetadata implements Metadata {

    // Vorbis Comment Field Names
    // see http://www.xiph.org/vorbis/doc/v-comment.html

    /**
     * Track/Work name
     */
    public static final String TITLE = "TITLE";
    /**
     * The version field may be used to differentiate multiple versions
     * of the same track title in a single collection. (e.g. remix info)
     */
    public static final String VERSION = "VERSION";
    /**
     * The collection name to which this track belongs
     */
    public static final String ALBUM = "ALBUM";
    /**
     * The track number of this piece if part of a specific larger
     * collection or album
     */
    public static final String TRACKNUMBER = "TRACKNUMBER";
    /**
     * The artist generally considered responsible for the work. In
     * popular music this is usually the performing band or singer.
     * For classical music it would be the composer. For an audio
     * book it would be the author of the original text.
     */
    public static final String ARTIST = "ARTIST";
    /**
     * The artist(s) who performed the work. In classical music this
     * would be the conductor, orchestra, soloists. In an audio book
     * it would be the actor who did the reading. In popular music
     * this is typically the same as the ARTIST and is omitted.
     */
    public static final String PERFORMER = "PERFORMER";
    /**
     * Copyright attribution, e.g., '2001 Nobody's Band' or
     * '1999 Jack Moffitt'
     */
    public static final String COPYRIGHT = "COPYRIGHT";
    /**
     * License information, eg, 'All Rights Reserved',
     * 'Any Use Permitted', a URL to a license such as a Creative
     * Commons license ("www.creativecommons.org/blahblah/license.html")
     * or the EFF Open Audio License ('distributed under the terms of
     * the Open Audio License. see http://www.eff.org/IP/Open_licenses/eff_oal.html
     * for details'), etc.
     */
    public static final String LICENSE = "LICENSE";
    /**
     * Name of the organization producing the track
     * (i.e. the 'record label')
     */
    public static final String ORGANIZATION = "ORGANIZATION";
    /**
     * A short text description of the contents
     */
    public static final String DESCRIPTION = "DESCRIPTION";
    /**
     * A short text indication of music genre
     */
    public static final String GENRE = "GENRE";
    /**
     * Date the track was recorded
     */
    public static final String DATE = "DATE";
    /**
     * Location where track was recorded
     */
    public static final String LOCATION = "LOCATION";
    /**
     * Contact information for the creators or distributors of the track.
     * This could be a URL, an email address, the physical address of
     * the producing label.
     */
    public static final String CONTACT = "CONTACT";
    /**
     * ISRC number for the track; see <a href='http://www.ifpi.org/isrc/'>
     * the ISRC intro page</a> for more information on ISRC numbers.
     */
    public static final String ISRC = "ISRC";

    // Other Common Fields
    /**
     * The artist for the album as a whole. If the artist is
     * consistent throughout all tracks on an album, this field
     * is generally left blank
     */
    public static final String ALBUMARTIST = "ALBUMARTIST";

    // ******************************
    // Members
    // ******************************

    private final FlacMetadataBlockStreamInfo streamInfoBlock;
    private FlacMetadataBlockSeektable seektableBlock; // assume at most 1 seektable??
    private FlacMetadataBlockCuesheet cuesheetBlock; // assume at most 1 cuesheet??
    private final List<FlacMetadataBlockApplication> applicationBlocks = new ArrayList<>();
    private final List<FlacMetadataBlockVorbisComment> vorbisCommentBlocks = new ArrayList<>();
    private final List<FlacMetadataBlockPicture> pictureBlocks = new ArrayList<>();

    // ******************************
    // Constructors
    // ******************************

    private FlacMetadata(FlacMetadataBlockStreamInfo streamInfoBlock) {
        this.streamInfoBlock = streamInfoBlock;
    }

    // ******************************
    // Getters
    // ******************************

    public long getLength() {
        long blocksLenghth = 4 + streamInfoBlock.getBlockLength();

        if (seektableBlock != null)
            blocksLenghth += 4 + seektableBlock.getBlockLength();

        if (cuesheetBlock != null)
            blocksLenghth += 4 + cuesheetBlock.getBlockLength();

        blocksLenghth += applicationBlocks.stream()
                .map(block -> 4 + block.getBlockLength())
                .reduce((a, b) -> a + b)
                .orElse(0);
        blocksLenghth += vorbisCommentBlocks.stream()
                .map(block -> 4 + block.getBlockLength())
                .reduce((a, b) -> a + b)
                .orElse(0);
        blocksLenghth += pictureBlocks.stream()
                .map(block -> 4 + block.getBlockLength())
                .reduce((a, b) -> a + b)
                .orElse(0);

        return 4 + blocksLenghth;
    }

    public FlacMetadataBlockStreamInfo getStreamInfoBlock() {
        return streamInfoBlock;
    }

    public FlacMetadataBlockSeektable getSeektable() {
        return seektableBlock;
    }

    public FlacMetadataBlockCuesheet getCuesheet() {
        return cuesheetBlock;
    }

    public List<FlacMetadataBlockApplication> getApplicationBlocks() {
        return Collections.unmodifiableList(applicationBlocks);
    }

    public List<FlacMetadataBlockVorbisComment> getVorbisCommentBlocks() {
        return Collections.unmodifiableList(vorbisCommentBlocks);
    }

    public List<FlacMetadataBlockPicture> getPictureBlocks() {
        return Collections.unmodifiableList(pictureBlocks);
    }

    // ******************************
    // Metadata Methods
    // ******************************

    /**
     * By default, assume that if the generic {@code get()} method
     * is being invoked, we're after a specific piece of metadata
     * which is probably going to be contained in a VorbisComment
     *
     * @param key the metadata key
     * @return the metadata value
     */
    @Override
    public String get(String key) {
        return vorbisCommentBlocks.stream()
                .map(FlacMetadataBlockVorbisComment::getComments)
                .flatMap(List::stream)
                .filter(comment -> key.equalsIgnoreCase(comment.key))
                .map(comment -> comment.value)
                .collect(Collectors.joining(";"));
    }

    // ******************************
    // Public Builder Methods
    // ******************************

    public static FlacMetadata build(FlacMetadataBlockStreamInfo streamInfo, FlacMetadataBlock... otherBlocks) {
        return build(streamInfo, Arrays.asList(otherBlocks));
    }

    public static FlacMetadata build(FlacMetadataBlockStreamInfo streamInfo, List<FlacMetadataBlock> otherBlocks) {

        FlacMetadata metadata = new FlacMetadata(streamInfo);

        for (FlacMetadataBlock block : otherBlocks) {

            if (block instanceof FlacMetadataBlockPadding)
                continue; // Skip padding completely!

            if (block instanceof FlacMetadataBlockSeektable) {
                if (metadata.seektableBlock != null)
                    throw new AssertionError("Expected only a single SEEKTABLE block per file?");
                metadata.seektableBlock = (FlacMetadataBlockSeektable) block;
                continue;
            }

            if (block instanceof FlacMetadataBlockCuesheet) {
                if (metadata.cuesheetBlock != null)
                    throw new AssertionError("Expected only a single CUESHEET block per file?");
                metadata.cuesheetBlock = (FlacMetadataBlockCuesheet) block;
                continue;
            }

            if (block instanceof FlacMetadataBlockApplication) {
                metadata.applicationBlocks.add((FlacMetadataBlockApplication) block);
                continue;
            }

            if (block instanceof FlacMetadataBlockVorbisComment) {
                metadata.vorbisCommentBlocks.add((FlacMetadataBlockVorbisComment) block);
                continue;
            }

            if (block instanceof FlacMetadataBlockPicture) {
                metadata.pictureBlocks.add((FlacMetadataBlockPicture) block);
                continue;
            }

            throw new IllegalArgumentException("Unexpected block type: " + block.getClass());

        }

        return metadata;

    }

}
