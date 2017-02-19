package net.anomalyxii.mediatools.local.builders;

import net.anomalyxii.mediatools.api.builders.ArtistBuilder;
import net.anomalyxii.mediatools.local.LocalArtist;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * An {@link ArtistBuilder} for building a {@link LocalArtist}.
 */
public class LocalArtistBuilder implements ArtistBuilder<Long> {

    // *********************************
    // Members
    // *********************************

    private final AtomicLong id;

    private String name;

    // *********************************
    // Constructors
    // *********************************

    public LocalArtistBuilder(AtomicLong id) {
        this.id = Objects.requireNonNull(id);
    }

    // *********************************
    // ArtistBuilder Methods
    // *********************************

    @Override
    public LocalArtistBuilder withName(String name) {
        return this;
    }

    @Override
    public LocalArtist build() {
        Objects.requireNonNull(name, "Artist Name must be set");
        return new LocalArtist(id.getAndIncrement(), name);
    }

}
