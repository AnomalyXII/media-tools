package net.anomalyxii.mediatools.api.builders;

import net.anomalyxii.mediatools.api.models.*;

import java.io.Serializable;
import java.net.URI;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;

/**
 * Abstract {@link LibraryBuilder} implementation.
 * <p>
 * Created by Anomaly on 18/02/2017.
 */
public abstract class AbstractLibraryBuilder<T extends Serializable> implements LibraryBuilder<T> {

    // ******************************
    // Members
    // ******************************

    private final Supplier<MutableLibrary<T>> factory;

    private final List<Source> sources = new ArrayList<>();
    private final Set<LibraryBuilderListener<T>> listeners = new HashSet<>();

    // ******************************
    // Constructors
    // ******************************

    public AbstractLibraryBuilder(Supplier<MutableLibrary<T>> factory) {
        this.factory = factory;
    }

    // ******************************
    // LibraryBuilder Methods
    // ******************************


    @Override
    public LibraryBuilder<T> withListener(LibraryBuilderListener<T> listener) {
        listeners.add(listener);
        return this;
    }

    @Override
    public LibraryBuilder<T> withListeners(LibraryBuilderListener<T>... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
        return this;
    }

    @Override
    public LibraryBuilder<T> withSource(URI source) {
        sources.add(new Source(source));
        return this;
    }

    @Override
    public LibraryBuilder<T> withSource(Path source) {
        sources.add(new Source(source));
        return this;
    }

    @Override
    public Library<T> build() {
        MutableLibrary<T> library = factory.get();
        fireOnBuildStartedEvent(library);

        for (Source source : sources) {
            try {
                processSource(library, source);
            } catch (Exception e) {
                fireOnErrorEvent(library, e);
            }
        }

        fireOnBuildFinishedEvent(library);
        return library;
    }

    // ******************************
    // Abstract Methods
    // ******************************

    protected abstract void processSource(MutableLibrary<T> library, Source source) throws Exception;

    // ******************************
    // Listener Methods
    // ******************************

    /**
     * Send the {@link LibraryBuilderListener#onBuildStarted(Library)}
     * event to all listeners.
     *
     * @param library the {@link Library} being built
     */
    protected void fireOnBuildStartedEvent(Library<T> library) {
        listeners.forEach(listener -> listener.onBuildStarted(library));
    }

    /**
     * Send the {@link LibraryBuilderListener#onBuildFinished(Library)}
     * event to all listeners.
     *
     * @param library the {@link Library} being built
     */
    protected void fireOnBuildFinishedEvent(Library<T> library) {
        listeners.forEach(listener -> listener.onBuildFinished(library));
    }

    /**
     * Send the {@link LibraryBuilderListener#onArtistAdded(Library, Artist)}
     * event to all listeners.
     *
     * @param library the {@link Library} being built
     * @param artist the {@link Artist} that was added
     */
    protected void fireOnArtistAdded(Library<T> library, Artist<T> artist) {
        listeners.forEach(listener -> listener.onArtistAdded(library, artist));
    }

    /**
     * Send the {@link LibraryBuilderListener#onArtistRemoved(Library, Artist)}
     * event to all listeners.
     *
     * @param library the {@link Library} being built
     * @param artist the {@link Artist} that was removed
     */
    protected void fireOnArtistRemoved(Library<T> library, Artist<T> artist) {
        listeners.forEach(listener -> listener.onArtistRemoved(library, artist));
    }

    /**
     * Send the {@link LibraryBuilderListener#onAlbumAdded(Library, Album)}
     * event to all listeners.
     *
     * @param library the {@link Library} being built
     * @param album the {@link Album} that was added
     */
    protected void fireOnAlbumAdded(Library<T> library, Album<T> album) {
        listeners.forEach(listener -> listener.onAlbumAdded(library, album));
    }

    /**
     * Send the {@link LibraryBuilderListener#onAlbumRemoved(Library, Album)}
     * event to all listeners.
     *
     * @param library the {@link Library} being built
     * @param album the {@link Album} that was removed
     */
    protected void fireOnAlbumRemoved(Library<T> library, Album<T> album) {
        listeners.forEach(listener -> listener.onAlbumRemoved(library, album));
    }

    /**
     * Send the {@link LibraryBuilderListener#onSongAdded(Library, Song)}
     * event to all listeners.
     *
     * @param library the {@link Library} being built
     * @param song the {@link Song} that was added
     */
    protected void fireOnSongAdded(Library<T> library, Song<T> song) {
        listeners.forEach(listener -> listener.onSongAdded(library, song));
    }

    /**
     * Send the {@link LibraryBuilderListener#onSongRemoved(Library, Song)}
     * event to all listeners.
     *
     * @param library the {@link Library} being built
     * @param song the {@link Song} that was removed
     */
    protected void fireOnSongRemoved(Library<T> library, Song<T> song) {
        listeners.forEach(listener -> listener.onSongRemoved(library, song));
    }

    /**
     * Send the {@link LibraryBuilderListener#onError(Library, Exception)}
     * event to all listeners.
     *
     * @param library the {@link Library} being built
     * @param e       the {@link Exception} that was raised
     */
    protected void fireOnErrorEvent(Library<T> library, Exception e) {
        listeners.forEach(listener -> listener.onError(library, e));
    }

    // ******************************
    // Helper Classes
    // ******************************

    /**
     * A simple container for a source of
     * {@link net.anomalyxii.mediatools.api.models.AudioFile audios}.
     * <p>
     * One of {@code uri} or {@code path} will always be set - but
     * not both.
     */
    protected static class Source {

        // Members

        public final URI uri;
        public final Path path;

        // Constructors

        public Source(URI uri) {
            this.uri = uri;
            this.path = null;
        }

        public Source(Path path) {
            this.path = path;
            this.uri = null;
        }

    }


}
