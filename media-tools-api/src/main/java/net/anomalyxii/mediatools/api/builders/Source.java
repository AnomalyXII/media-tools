package net.anomalyxii.mediatools.api.builders;

import net.anomalyxii.mediatools.api.exceptions.MediaException;
import net.anomalyxii.mediatools.api.AudioFile;

import java.net.URI;
import java.util.List;

/**
 * A simple container for a source of {@link AudioFile audios}.
 */
public interface Source {

    /**
     * Check if this {@code Source} is a leaf node, i.e. represents a single
     * {@link AudioFile}.
     *
     * @return {@literal true} if this {@code Source} is a leaf
     */
    boolean isLeaf();

    /**
     * Expand this {@code Source} into a {@link List} of all the child
     * {@link Source Sources} contained within.
     *
     * @return
     */
    List<Source> expand();

    /**
     * Convert the {@code Source} to a {@link URI}.
     *
     * @return the {@link URI} of the source
     */
    URI toUri();

    /**
     * Convert the {@code Source} to an {@link AudioFile}.
     *
     * @return an {@link AudioFile} generated from this {@code Source}
     * @throws MediaException if anything goes wrong reading in the audio file data
     */
    AudioFile toAudioFile() throws MediaException;

}
