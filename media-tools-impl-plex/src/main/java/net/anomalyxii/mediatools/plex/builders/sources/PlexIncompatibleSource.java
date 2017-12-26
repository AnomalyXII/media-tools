package net.anomalyxii.mediatools.plex.builders.sources;

import net.anomalyxii.mediatools.api.AudioFile;
import net.anomalyxii.mediatools.api.builders.Source;
import net.anomalyxii.mediatools.api.exceptions.MediaException;
import net.anomalyxii.mediatools.plex.builders.PlexResolver;
import net.anomalyxii.mediatools.plex.builders.models.Metadata;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A {@link Source} this is based on some Plex {@link Metadata} but is
 * not compatible with these tools.
 */
public class PlexIncompatibleSource extends PlexMetadataSource {

    // *********************************
    // Constructors
    // *********************************

    public PlexIncompatibleSource(URI root, Metadata metadata, PlexResolver resolver) {
        super(root, metadata, resolver);
    }

    // *********************************
    // Getters & Setters
    // *********************************

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public List<Source> expand() {
        return Collections.emptyList();
    }

    @Override
    public AudioFile toAudioFile() throws MediaException {
        throw new MediaException("Cannot convert a incompatible Source to an AudioFile");
    }

}
