package net.anomalyxii.mediatools.plex.builders;

import net.anomalyxii.mediatools.api.builders.Source;
import net.anomalyxii.mediatools.api.exceptions.MediaException;

import java.net.URI;
import java.util.List;

/**
 * A way resolving remote Plex directories.
 */
public interface PlexResolver {

    // *********************************
    // Interface Methods
    // *********************************

    /**
     * Resolve the URI into a {@link List} of {@link Source sources}.
     *
     * @param root the {@link URI} of the Plex library
     * @return a {@link List} of {@link Source sources}
     */
    List<Source> resolveLibrary(URI root) throws MediaException;

    /**
     * Resolve the URI into a {@link List} of {@link Source sources}.
     *
     * @param root the {@link URI} of the Plex library
     * @return a {@link List} of {@link Source sources}
     */
    List<Source> resolveLibrary(URI root, int id) throws MediaException;

    /**
     * Resolve the URI into a {@link List} of {@link Source sources}.
     *
     * @param root the {@link URI} of the Plex library
     * @return a {@link List} of {@link Source sources}
     */
    List<Source> resolveMetadata(URI root, int id) throws MediaException;

}
