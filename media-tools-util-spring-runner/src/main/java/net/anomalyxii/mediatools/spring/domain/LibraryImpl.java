package net.anomalyxii.mediatools.spring.domain;

import net.anomalyxii.mediatools.api.exceptions.LibraryException;
import net.anomalyxii.mediatools.api.models.Album;
import net.anomalyxii.mediatools.api.models.Artist;
import net.anomalyxii.mediatools.api.models.MutableLibrary;
import net.anomalyxii.mediatools.api.models.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anomaly on 18/02/2017.
 */
public class LibraryImpl implements MutableLibrary<Long> {


    // ******************************
    // Members
    // ******************************

    private final RepositoryContext context;

    // ******************************
    // Constructors
    // ******************************

    public LibraryImpl() {
        this(new RepositoryContext());
    }

    public LibraryImpl(RepositoryContext context) {
        this.context = context;
    }

    // ******************************
    // Library Methods
    // ******************************

    @Override
    public List<Artist<Long>> artists() {
        return new ArrayList<>(context.getArtistIndex());
    }

    @Override
    public List<Album<Long>> albums() {
        return new ArrayList<>(context.getAlbumIndex());
    }

    @Override
    public List<Song<Long>> songs() {
        return new ArrayList<>(context.getSongIndex());
    }

    // ******************************
    // MutableLibrary Methods
    // ******************************

    @Override
    public Song<Long> add(Song<Long> song) throws LibraryException {
        // No-op?
        return song;
    }

    @Override
    public Song<Long> remove(Song<Long> song) throws LibraryException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
