package net.anomalyxii.mediatools.plex.builders;

import net.anomalyxii.mediatools.api.builders.AbstractLibraryBuilder;
import net.anomalyxii.mediatools.api.builders.LibraryBuilder;
import net.anomalyxii.mediatools.api.builders.Source;
import net.anomalyxii.mediatools.api.exceptions.MediaException;
import net.anomalyxii.mediatools.plex.PlexLibrary;
import net.anomalyxii.mediatools.plex.builders.sources.PlexSource;
import net.anomalyxii.mediatools.plex.builders.unirest.UnirestPlexResolver;

import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * A {@link LibraryBuilder} for building a Plex library.
 */
public class PlexLibraryBuilder extends AbstractLibraryBuilder<String> {

    // *********************************
    // Members
    // *********************************

    private Source source;
    private PlexResolver resolver = new UnirestPlexResolver(); // Todo: make this flexible?

    // *********************************
    // LibraryBuilder Methods
    // *********************************

    @Override
    public PlexLibraryBuilder withSource(URI source) {
        return withSource(new PlexSource(source, resolver));
    }

    @Override
    public PlexLibraryBuilder withSource(Path source) {
        throw new IllegalArgumentException("Cannot build a Plex library from a Path - use URI instead!");
    }

    @Override
    public PlexLibraryBuilder withSource(Source source) {
        if (!(source instanceof PlexSource))
            throw new IllegalArgumentException("Invalid Source type: '" + source.getClass() + "'!");

        synchronized (this) {
            if (this.source != null)
                throw new IllegalStateException("Plex Source already set - currently we can only build one library at a time!");
            this.source = Objects.requireNonNull(source, "Plex Source must not be null!");
        }
        return this;
    }

    @Override
    public PlexLibrary build() throws MediaException {
        Objects.requireNonNull(source, "Cannot build Plex Library - no Plex Source specified!");

        PlexLibrary library = new PlexLibrary();
        fireOnBuildStarted(library);

        List<Source> allSources = source.expand();

        System.out.println("Expanded source to " + allSources.size() + " sources");
        for(Source source : allSources) {
            System.out.println(source.toAudioFile());
        }

        fireOnBuildFinished(library);
        return library;
    }

    // *********************************
    // LibraryBuilder Methods
    // *********************************

    private void getRootMediaContainer(URI uri) {
        // Todo: do something here?
    }

}
