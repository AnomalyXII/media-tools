package net.anomalyxii.mediatools.spring.domain;

import net.anomalyxii.mediatools.api.builders.AbstractLibraryBuilder;
import net.anomalyxii.mediatools.api.models.*;

import java.nio.file.*;
import java.util.function.Supplier;

/**
 * Load a music {@link Library} by scanning the local file
 * system to detect {@link Song songs} and then deriving
 * further information from the ID3 tags (or similar meta
 * information).
 * <p>
 * Use Spring Data repositories to persist the data.
 * <p>
 * Created by Anomaly on 18/02/2017.
 */
public class LibraryBuilderImpl extends AbstractLibraryBuilder<Long> {

    // ******************************
    // Members
    // ******************************

    private final RepositoryContext context;

    // ******************************
    // Constructors
    // ******************************

    public LibraryBuilderImpl() {
        this(new RepositoryContext());
    }

    public LibraryBuilderImpl(RepositoryContext context) {
        this(context, () -> new LibraryImpl(context));
    }

    public LibraryBuilderImpl(Supplier<MutableLibrary<Long>> factory) {
        this(new RepositoryContext(), factory);
    }

    public LibraryBuilderImpl(RepositoryContext context, Supplier<MutableLibrary<Long>> factory) {
        super(factory);
        this.context = context;
    }

    // ******************************
    // AbstractLibraryBuilder Methods
    // ******************************

    @Override
    protected Artist<Long> findOrCreateArtist(AudioFile audioFile) {
        return context.findOrCreateArtist(audioFile.getArtistName());
    }

    @Override
    protected Album<Long> findOrCreateAlbum(Artist<Long> artist, AudioFile audioFile) {
        boolean hasAlbumArtist = audioFile.getAlbumArtistName() == null
                                 || audioFile.getAlbumArtistName().isEmpty();
        Artist<Long> albumArtist = hasAlbumArtist ? artist : context.findOrCreateArtist(audioFile.getAlbumArtistName());
        return context.findOrCreateAlbum(audioFile.getAlbumName(), albumArtist);
    }

    @Override
    protected Song<Long> findOrCreateSong(int track, String title, Artist<Long> artist, Album<Long> album, AudioFile audioFile) {
        Path relativeFile = Paths.get(audioFile.getSource());
        return context.findOrCreateSong(relativeFile.toString(), track, title, artist, album);
    }

    // ******************************
    // Helper Classes
    // ******************************


}
