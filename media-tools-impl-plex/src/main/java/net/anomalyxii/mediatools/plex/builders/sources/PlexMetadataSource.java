package net.anomalyxii.mediatools.plex.builders.sources;

import net.anomalyxii.mediatools.api.AudioFile;
import net.anomalyxii.mediatools.api.builders.Source;
import net.anomalyxii.mediatools.api.exceptions.MediaException;
import net.anomalyxii.mediatools.plex.builders.PlexResolver;
import net.anomalyxii.mediatools.plex.builders.models.Metadata;

import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * A {@link Source} this is based on some Plex {@link Metadata}.
 */
public class PlexMetadataSource implements Source {

    // *********************************
    // Members
    // *********************************

    private final URI root;
    private final Metadata metadata;
    private final PlexResolver resolver;

    // *********************************
    // Constructors
    // *********************************

    public PlexMetadataSource(URI root, Metadata metadata, PlexResolver resolver) {
        this.root = Objects.requireNonNull(root);
        this.metadata = Objects.requireNonNull(metadata);
        this.resolver = Objects.requireNonNull(resolver);
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
        return resolver.resolveMetadata(root, Integer.parseInt(metadata.ratingKey));
    }

    @Override
    public URI toUri() {
        return root.resolve(metadata.key);
    }

    @Override
    public AudioFile toAudioFile() throws MediaException {
        throw new MediaException("Cannot convert a non-leaf Source to an AudioFile");
    }

    // *********************************
    // Protected Methods
    // *********************************

    /**
     * Get the backing {@link Metadata}.
     *
     * @return the {@link Metadata}
     */
    protected Metadata getMetadata() {
        return metadata;
    }

    // *********************************
    // To String
    // *********************************

    @Override
    public String toString() {
        return String.format("PlexMetadataSource[%s]", metadata.title);
    }

}
