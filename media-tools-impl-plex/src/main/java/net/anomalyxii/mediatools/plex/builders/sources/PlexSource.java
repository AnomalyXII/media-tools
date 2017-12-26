package net.anomalyxii.mediatools.plex.builders.sources;

import net.anomalyxii.mediatools.api.AudioFile;
import net.anomalyxii.mediatools.api.builders.Source;
import net.anomalyxii.mediatools.api.exceptions.MediaException;
import net.anomalyxii.mediatools.api.readers.AudioFileReader;
import net.anomalyxii.mediatools.api.readers.AudioFileReaders;
import net.anomalyxii.mediatools.plex.builders.PlexResolver;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * A {@link Source} for Plex-based media.
 */
public class PlexSource implements Source {

    // *********************************
    // Private Members
    // *********************************

    private final URI root;
    private final PlexResolver resolver;

    // *********************************
    // Constructors
    // *********************************

    public PlexSource(URI root, PlexResolver resolver) {
        // Todo: should we check for non-root URIs?
        this.root = Objects.requireNonNull(root);
        this.resolver = resolver;
    }

    // *********************************
    // Source Methods
    // *********************************

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public List<Source> expand() throws MediaException {
        return resolver.resolveLibrary(root);
    }

    @Override
    public URI toUri() {
        return root;
    }

    @Override
    public AudioFile toAudioFile() throws MediaException {
        throw new MediaException("Cannot convert a non-leaf Source to an AudioFile");
    }

}
