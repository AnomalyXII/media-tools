package net.anomalyxii.mediatools.local.builders;

import net.anomalyxii.mediatools.api.*;
import net.anomalyxii.mediatools.api.builders.*;
import net.anomalyxii.mediatools.api.exceptions.LibraryException;
import net.anomalyxii.mediatools.local.LocalLibrary;
import net.anomalyxii.mediatools.local.builders.contexts.MapBasedLibraryContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract {@link LibraryBuilder} implementation.
 * <p>
 * Created by Anomaly on 18/02/2017.
 */
public class LocalLibraryBuilder implements LibraryBuilder<Long> {

    private final Logger logger = LoggerFactory.getLogger(LocalLibraryBuilder.class);

    // *********************************
    // Members
    // *********************************

    private final AtomicLong artistCounter = new AtomicLong(0);
    private final AtomicLong albumCounter = new AtomicLong(0);
    private final AtomicLong songCounter = new AtomicLong(0);

    private final List<Source> sources = new ArrayList<>();
    private final List<LibraryBuilderListener<Long>> listeners = new ArrayList<>();

    // *********************************
    // Constructors
    // *********************************

    public LocalLibraryBuilder() {
    }

    // *********************************
    // LibraryBuilder Methods
    // *********************************

    @Override
    public LibraryBuilder<Long> withListener(LibraryBuilderListener<Long> listener) {
        listeners.add(listener);
        return this;
    }

    @Override
    public LibraryBuilder<Long> withListeners(LibraryBuilderListener<Long>... listener) {
        listeners.addAll(Arrays.asList(listener));
        return this;
    }

    @Override
    public LibraryBuilder<Long> withSource(URI source) {
        return withSource(new URISource(source));
    }

    @Override
    public LibraryBuilder<Long> withSource(Path source) {
        return withSource(new PathSource(source));
    }

    @Override
    public LibraryBuilder<Long> withSource(Source source) {
        sources.add(source);
        return this;
    }

    @Override
    public Library<Long> build() {
        // Todo: make this implementation pluggable!
        LibraryContext<Long> context = new MapBasedLibraryContext<>();

        MutableLibrary<Long> library = new LocalLibrary(context);
        fireOnBuildStartedEvent(library);

        // Expand all the Sources into only leaf nodes
        List<Source> expandedSources = sources.stream()
                .map(Source::expand)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        logger.debug("Expanded original {} sources to {}", sources.size(), expandedSources.size());

        // Process each leaf
        for (int i = 0; i < expandedSources.size(); i++) {
            Source source = expandedSources.get(i);
            logger.debug("Processing {} ({} of {})", source, i, expandedSources.size());
            try {
                AudioFile audioFile = source.toAudioFile();
                logger.debug("Successfully read AudioFile from '{}'", source);

                String artistName = parseArtist(audioFile);
                Artist<Long> artist = findOrCreateArtistByName(context, artistName);

                String albumArtistName = parseAlbumArtist(audioFile);
                Artist<Long> albumArtist = findOrCreateArtistByName(context, albumArtistName);

                String albumName = parseAlbum(audioFile);
                Album<Long> album = findOrCreateAlbumByName(context, albumName, albumArtist, artist);

                int trackNumber = parseTrackNumber(audioFile);
                String title = parseTitle(audioFile);
                Song<Long> song = findOrCreateSongByTrackAndTitle(context, trackNumber, title, artist, album);
                logger.debug("Successfully parsed Song from '{}' -> [{}] [{}] [{}]",
                        source,
                        song.getTrackNumber(),
                        song.getAlbum(),
                        song.getArtist());
            } catch (Exception e) {
                fireOnErrorEvent(library, e);
            }
        }

        fireOnBuildFinishedEvent(library);
        return library;
    }

    // *********************************
    // Overridable Methods
    // *********************************

    // Todo: all of these should probably return an Optional...

    /**
     * Extract the {@code Track Number} from an {@link AudioFile}.
     *
     * @param audioFile the {@link AudioFile} to parse
     * @return the {@code Track Number}
     */
    protected int parseTrackNumber(AudioFile audioFile) {
        return Integer.parseInt(audioFile.getTrackNumber());
    }

    /**
     * Extract the {@code Title} from an {@link AudioFile}.
     *
     * @param audioFile the {@link AudioFile} to parse
     * @return the {@code Title}
     */
    protected String parseTitle(AudioFile audioFile) {
        return audioFile.getTitle();
    }

    /**
     * Extract the {@code Artist} name from an {@link AudioFile}.
     *
     * @param audioFile the {@link AudioFile} to parse
     * @return the {@code Artist} name
     */
    protected String parseArtist(AudioFile audioFile) {
        return audioFile.getArtistName();
    }

    /**
     * Extract the {@code Album Artist} name from an {@link AudioFile}.
     *
     * @param audioFile the {@link AudioFile} to parse
     * @return the {@code Album Artist} name
     */
    protected String parseAlbumArtist(AudioFile audioFile) {
        return audioFile.getAlbumArtistName();
    }

    /**
     * Extract the {@code Album} name from an {@link AudioFile}.
     *
     * @param audioFile the {@link AudioFile} to parse
     * @return the {@code Album} name
     */
    protected String parseAlbum(AudioFile audioFile) {
        return audioFile.getAlbumName();
    }

    // *********************************
    // Builder Methods Methods
    // *********************************

    /*
     * Create a new ArtistBuilder.
     */
    private LocalArtistBuilder newArtistBuilder() {
        return new LocalArtistBuilder(artistCounter);
    }

    /*
     * Create a new AlbumBuilder.
     */
    public AlbumBuilder<Long> newAlbumBuilder() {
        return null;
    }

    /*
     * Create a new SongBuilder.
     */
    public SongBuilder<Long> newSongBuilder() {
        return null;
    }

    // *********************************
    // Listener Methods
    // *********************************

    /**
     * Send the {@link LibraryBuilderListener#onBuildStarted(Library)}
     * event to all listeners.
     *
     * @param library the {@link Library} being built
     */
    protected void fireOnBuildStartedEvent(Library<Long> library) {
        listeners.forEach(listener -> listener.onBuildStarted(library));
    }

    /**
     * Send the {@link LibraryBuilderListener#onBuildFinished(Library)}
     * event to all listeners.
     *
     * @param library the {@link Library} being built
     */
    protected void fireOnBuildFinishedEvent(Library<Long> library) {
        listeners.forEach(listener -> listener.onBuildFinished(library));
    }

    /**
     * Send the {@link LibraryBuilderListener#onError(Library, Exception)}
     * event to all listeners.
     *
     * @param library the {@link Library} being built
     * @param e       the {@link Exception} that was raised
     */
    protected void fireOnErrorEvent(Library<Long> library, Exception e) {
        listeners.forEach(listener -> listener.onError(library, e));
    }

    // Todo: these methods violate CQS - maybe there's a better way to do this??

    /**
     * Find a previously created {@link Artist}. If the {@link Artist} does
     * not already exist, create a new one with the specified name.
     *
     * @param context
     * @param artistName the name of the {@link Artist} to find or create
     * @return the {@link Artist}
     * @throws LibraryException if something goes wrong finding or creating the {@link Artist}
     */
    public Artist<Long> findOrCreateArtistByName(LibraryContext<Long> context, String artistName)
            throws LibraryException {
        return findOrCreate(
                () -> context.findArtistByName(artistName),
                () -> newArtistBuilder().withName(artistName).build(),
                () -> artistName
        );
    }

    /**
     * Find a previously created {@link Album}. If the {@link Album} does
     * not already exist, create a new one with the specified name.
     *
     * @param albumName   the name of the {@link Album} to find or create
     * @param albumArtist the primary {@link Artist} for the {@link Album}
     * @param artist      the {@link Artist} of the current track
     * @return the {@link Album}
     * @throws LibraryException if something goes wrong finding or creating the {@link Album}
     */
    public Album<Long> findOrCreateAlbumByName(LibraryContext<Long> context, String albumName,
                                               Artist<Long> albumArtist, Artist<Long> artist) throws LibraryException {
        return findOrCreate(
                () -> context.findAlbumByArtistAndTitle(albumArtist, albumName),
                () -> newAlbumBuilder().withTitle(albumName).withArtist(albumArtist).build(),
                () -> String.format("%s::%s", albumArtist.getId(), albumName)
        );
    }

    /**
     * Find a previously created {@link Song}. If the {@link Song} does
     * not already exist, create a new one with the specified name.
     *
     * @param track  the track number
     * @param title  the title of the track
     * @param artist the {@link Artist} of the track
     * @param album  the {@link Album}
     * @return the {@link Song}
     * @throws LibraryException if something goes wrong finding or creating the {@link Song}
     */
    public Song<Long> findOrCreateSongByTrackAndTitle(LibraryContext<Long> context, int track, String title,
                                                      Artist<Long> artist, Album<Long> album) throws LibraryException {
        return findOrCreate(
                () -> context.findSongByAlbumAndTrackNumber(album, track),
                () -> newSongBuilder().withTrackNumber(track).withTitle(title).build(),
                () -> String.format("%s::%s::%s", artist.getId(), album.getId(), track)
        );
    }

    // *********************************
    // Private Methods
    // *********************************

    /**
     * A modification of the {@link Supplier} {@link FunctionalInterface}
     * that can throw a {@link LibraryException} if something goes wrong.
     *
     * @param <X> the type of {@link Object} returned by this {@code LibrarySupplier}
     */
    @FunctionalInterface
    private interface LibrarySupplier<X> {

        /**
         * Gets a result.
         *
         * @return a result
         */
        X get() throws LibraryException;

    }

    /**
     * Look up an instance using the {@code findFunction}. If the instance
     * does not exist, create a new instance using the {@code createFunction}
     * and persist it.
     *
     * @param findFunction   the {@link LibrarySupplier} that can be used to find an existing instance
     * @param createFunction the {@link LibrarySupplier} that can be used to create a new instance
     * @param lockFunction   a {@link Supplier} that will produce an object to use for synchronization
     * @param <X>            the type of instance being found or created
     * @return an instance of type {@code <X>}
     */
    private <X> X findOrCreate(LibrarySupplier<Optional<X>> findFunction,
                               LibrarySupplier<X> createFunction,
                               Supplier<Object> lockFunction) throws LibraryException {

        Optional<X> existing = findFunction.get();
        if (existing.isPresent())
            return existing.get();

        synchronized (lockFunction.get()) {
            existing = findFunction.get();
            if (existing.isPresent())
                return existing.get();

            return createFunction.get();
        }

    }

    /**
     * A {@link Source} for a {@link URI}.
     */
    protected static class URISource extends AbstractSource {

        // Members

        public final URI uri;

        // Constructors

        public URISource(URI uri) {
            this.uri = uri;
        }

        // Source Methods

        @Override
        public boolean isLeaf() {
            return false;
        }

        @Override
        public List<Source> expand() {
            if ("file".equalsIgnoreCase(uri.getScheme()))
                return new PathSource(Paths.get(uri)).expand();
            else
                throw new UnsupportedOperationException("Not implemented yet!");
        }

        @Override
        public URI toUri() {
            return uri;
        }

    }

    /**
     * A {@link Source} for a {@link Path}.
     */
    protected static class PathSource extends AbstractSource {

        // Members

        public final Path path;

        // Constructors

        public PathSource(Path path) {
            this.path = path;
        }

        // Source Methods


        @Override
        public boolean isLeaf() {
            return !Files.isDirectory(path);
        }

        @Override
        public List<Source> expand() {
            if (isLeaf())
                return Collections.singletonList(this);

            if(!Files.isReadable(path))
                return null;

            try {
                if(Files.isHidden(path))
                    return null;
            } catch (IOException e) {
                return null;
            }

            Stream<Path> stream;
            try {
                stream = Files.list(path);
            } catch (IOException e) {
                return null;
            }

            return stream.map(PathSource::new)
                    .map(Source::expand)
                    .filter(Objects::nonNull) // Todo: should we really need to be checking for null??
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }

        @Override
        public URI toUri() {
            return path.toUri();
        }

    }


}
