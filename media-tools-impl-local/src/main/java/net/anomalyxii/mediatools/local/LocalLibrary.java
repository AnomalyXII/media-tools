package net.anomalyxii.mediatools.local;

import net.anomalyxii.mediatools.api.*;
import net.anomalyxii.mediatools.api.builders.LibraryContext;
import net.anomalyxii.mediatools.api.exceptions.LibraryException;

import java.util.Collections;
import java.util.List;

/**
 * A {@link Library} that can be used to manage media on a local
 * filesystem.
 * <p>
 * Created by Anomaly on 15/04/2016.
 */
public class LocalLibrary implements MutableLibrary<Long> {

    // ******************************
    // Members
    // ******************************

    private final LibraryContext<Long> context;

    // ******************************
    // Constructors
    // ******************************

    public LocalLibrary(LibraryContext<Long> context) {
        this.context = context;
    }

    // ******************************
    // Library Methods
    // ******************************

    @Override
    public List<Artist<Long>> artists() {
        return Collections.unmodifiableList(context.getArtists());
    }

    @Override
    public List<Album<Long>> albums() {
        return Collections.unmodifiableList(context.getAlbums());
    }

    @Override
    public List<Song<Long>> songs() {
        return Collections.unmodifiableList(context.getSongs());
    }

    // ******************************
    // MutableLibrary Methods
    // ******************************

    @Override
    public Song<Long> add(Song<Long> song) {
        return context.save(song);
    }

    @Override
    public Song<Long> remove(Song<Long> song) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
