package net.anomalyxii.mediatools.plex.builders.sources;

import net.anomalyxii.mediatools.api.AudioFile;
import net.anomalyxii.mediatools.api.builders.Source;
import net.anomalyxii.mediatools.api.exceptions.MediaException;
import net.anomalyxii.mediatools.plex.builders.PlexResolver;
import net.anomalyxii.mediatools.plex.builders.models.Directory;

import java.net.URI;
import java.util.List;
import java.util.Objects;

public class PlexLibrarySource implements Source {

    // *********************************
    // Members
    // *********************************

    private final URI root;
    private final Directory library;
    private final PlexResolver resolver;

    // *********************************
    // Constructors
    // *********************************

    public PlexLibrarySource(URI root, Directory library, PlexResolver resolver) {
        this.root = Objects.requireNonNull(root);
        this.library = Objects.requireNonNull(library);
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
        return resolver.resolveLibrary(root, Integer.parseInt(library.key));
    }

    @Override
    public URI toUri() {
        return root.resolve(String.format("/library/sections/%d/", Integer.parseInt(library.key)));
    }

    @Override
    public AudioFile toAudioFile() throws MediaException {
        throw new MediaException("Cannot convert a non-leaf Source to an AudioFile");
    }

    // *********************************
    // To String
    // *********************************

    @Override
    public String toString() {
        return String.format("PlexMetadataSource[%s]", library.title);
    }

}
