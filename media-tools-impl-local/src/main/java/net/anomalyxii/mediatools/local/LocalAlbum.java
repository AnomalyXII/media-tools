package net.anomalyxii.mediatools.local;

import net.anomalyxii.mediatools.api.Album;

/**
 * Created by Anomaly on 18/04/2016.
 */
public class LocalAlbum implements Album<Long> {

    // ******************************
    // Members
    // ******************************

    private final Long id;
    private String name;
    private LocalArtist artist;

    // ******************************
    // Constructors
    // ******************************

    public LocalAlbum(Long id, String name, LocalArtist artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
    }

    // ******************************
    // Album Methods
    // ******************************

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public LocalArtist getAlbumArtist() {
        return artist;
    }

}
