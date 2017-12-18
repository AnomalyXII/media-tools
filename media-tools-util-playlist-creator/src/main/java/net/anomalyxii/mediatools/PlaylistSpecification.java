package net.anomalyxii.mediatools;

import net.anomalyxii.mediatools.api.models.Playlist;

/**
 * Created by Anomaly on 24/04/2016.
 */
public interface PlaylistSpecification {

    // ******************************
    // Interface Methods
    // ******************************

    VerifyResult verify(Playlist<?> playlist);

    // ******************************
    // enum Constants
    // ******************************

    enum VerifyResult {

        SUCCESS,
        TOO_SHORT,
        TOO_LONG,
        TOO_MANY_TRACKS,
        TOO_FEW_TRACKS

    }

}
