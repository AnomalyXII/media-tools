package net.anomalyxii.mediatools.local;

import net.anomalyxii.mediatools.api.models.Artist;

/**
 * Created by Anomaly on 18/04/2016.
 */
public class LocalArtist implements Artist<Long> {

    // ******************************
    // Members
    // ******************************

    private final Long id;
    private String name;

    // ******************************
    // Constructors
    // ******************************

    public LocalArtist(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // ******************************
    // Artist Methods
    // ******************************

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

}
