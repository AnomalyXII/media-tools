package net.anomalyxii.mediatools.api.models;

import java.io.Serializable;
import java.util.List;

/**
 * A Playlist?
 * <p>
 * Created by Anomaly on 15/04/2016.
 */
public interface Playlist<ID extends Serializable> {

    // ******************************
    // Interface Methods
    // ******************************

    List<Song<ID>> getSongs();

}
