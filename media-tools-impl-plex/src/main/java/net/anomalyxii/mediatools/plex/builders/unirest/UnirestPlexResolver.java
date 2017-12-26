package net.anomalyxii.mediatools.plex.builders.unirest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.anomalyxii.mediatools.api.builders.Source;
import net.anomalyxii.mediatools.api.exceptions.MediaException;
import net.anomalyxii.mediatools.plex.builders.sources.PlexLibrarySource;
import net.anomalyxii.mediatools.plex.builders.sources.PlexIncompatibleSource;
import net.anomalyxii.mediatools.plex.builders.sources.PlexMetadataSource;
import net.anomalyxii.mediatools.plex.builders.PlexResolver;
import net.anomalyxii.mediatools.plex.builders.models.Directory;
import net.anomalyxii.mediatools.plex.builders.models.MediaContainer;
import net.anomalyxii.mediatools.plex.builders.models.MediaContainerResponse;
import net.anomalyxii.mediatools.plex.builders.models.Metadata;
import net.anomalyxii.mediatools.plex.builders.sources.PlexTrackSource;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A {@link PlexResolver} that uses the Unirest library to make requests.
 */
public class UnirestPlexResolver implements PlexResolver {

    // *********************************
    // PlexResolver Methods
    // *********************************

    @Override
    public List<Source> resolveLibrary(URI root) throws MediaException {
        URI libraryURI = root.resolve("/library/sections");

        try {
            Unirest.setObjectMapper(new JacksonObjectMapper());
            HttpResponse<MediaContainerResponse> httpResponse = Unirest.get(libraryURI.toString())
                    .header("Accept", "application/json")
                    .asObject(MediaContainerResponse.class);

            MediaContainerResponse response = httpResponse.getBody();

            MediaContainer container = response.mediaContainer;
            if (Objects.isNull(container))
                return Collections.emptyList();

            List<Directory> directories = container.directory;
            if (Objects.isNull(directories))
                return Collections.emptyList();

            return directories.parallelStream()
                    .filter(directory -> "Plex Music Scanner".equalsIgnoreCase(directory.scanner))
                    .map(directory -> convertDirectoryToSource(root, directory))
                    .map(this::expandSource)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        } catch (UnirestException e) {
            throw new MediaException(e);
        }
    }

    @Override
    public List<Source> resolveLibrary(URI root, int id) throws MediaException {
        URI libraryURI = root.resolve(String.format("/library/sections/%d/all", id));

        try {
            Unirest.setObjectMapper(new JacksonObjectMapper());
            HttpResponse<MediaContainerResponse> httpResponse = Unirest.get(libraryURI.toString())
                    .header("Accept", "application/json")
                    .asObject(MediaContainerResponse.class);

            MediaContainerResponse response = httpResponse.getBody();
            MediaContainer container = response.mediaContainer;
            if (Objects.isNull(container))
                return Collections.emptyList();

            List<Metadata> metadata = container.metadata;
            if (Objects.isNull(metadata))
                return Collections.emptyList();

            return metadata.parallelStream()
                    .map(md -> convertMetadataToSource(root, md))
                    .map(this::expandSource)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        } catch (UnirestException e) {
            throw new MediaException(e);
        }
    }

    @Override
    public List<Source> resolveMetadata(URI root, int id) throws MediaException {
        URI metadataURI = root.resolve(String.format("/library/metadata/%d/children", id));

        try {
            Unirest.setObjectMapper(new JacksonObjectMapper());
            HttpResponse<MediaContainerResponse> httpResponse = Unirest.get(metadataURI.toString())
                    .header("Accept", "application/json")
                    .asObject(MediaContainerResponse.class);

            MediaContainerResponse response = httpResponse.getBody();
            MediaContainer container = response.mediaContainer;

            List<Metadata> metadata = container.metadata;
            if (Objects.isNull(metadata))
                return Collections.emptyList();

            return metadata.parallelStream()
                    .map(md -> convertMetadataToSource(root, md))
                    .map(this::expandSource)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        } catch (UnirestException e) {
            throw new MediaException(e);
        }
    }

    // *********************************
    // Private Helper Methods
    // *********************************

    /*
     * Convert a Directory into an appropriate Source.
     */
    private Source convertDirectoryToSource(URI root, Directory directory) {
        return new PlexLibrarySource(root, directory, this);
    }

    /*
     * Convert a Directory into an appropriate Source.
     */
    private Source convertMetadataToSource(URI root, Metadata metadata) {
        switch (metadata.type.toLowerCase()) {
            case "track":
                return new PlexTrackSource(root, metadata, this);

            case "artist":
            case "album":
                return new PlexMetadataSource(root, metadata, this);

            default:
                return new PlexIncompatibleSource(root, metadata, this);
        }
    }

    /*
     * Expand a Source, sneakily throwing any exception
     */
    private List<Source> expandSource(Source source) {
        try {
            return source.expand();
        } catch (MediaException e) {
            throw sneakyThrow(e);
        }
    }

    /*
     * Sneaky Throw ;)
     */
    private static <E extends Throwable> Error sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }

}
