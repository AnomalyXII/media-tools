package net.anomalyxii.mediatools.local;

import net.anomalyxii.mediatools.api.exceptions.LibraryException;
import net.anomalyxii.mediatools.api.models.*;
import net.anomalyxii.mediatools.api.readers.AudioFileReader;
import net.anomalyxii.mediatools.api.readers.AudioFileReaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Anomaly on 15/04/2016.
 */
public class LocalLibrary implements MutableLibrary<Long> {

    // ******************************
    // Members
    // ******************************

    private final LocalContext context;

    // ******************************
    // Constructors
    // ******************************

    public LocalLibrary() {
        this(new LocalContext());
    }

    public LocalLibrary(LocalContext context) {
        this.context = context;
    }

    // ******************************
    // Library Methods
    // ******************************

    @Override
    public List<Artist<Long>> artists() {
        return context.getArtistIndex()
                .values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<Album<Long>> albums() {
        return context.getAlbumIndex()
                .values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<Song<Long>> songs() {
        return context.getSongIndex()
                .values()
                .stream()
                .collect(Collectors.toList());
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
