package net.anomalyxii.mediatools.local.readers.mp3;

import net.anomalyxii.mediatools.api.models.Metadata;

import java.util.Map;

/**
 * Created by Anomaly on 28/04/2016.
 */
public class Mp3Metadata implements Metadata {

    // ******************************
    // Members
    // ******************************

    private final Map<FrameType, String> data;

    // ******************************
    // Constructors
    // ******************************

    public Mp3Metadata(Map<FrameType, String> data) {
        this.data = data;
    }

    // ******************************
    // Constructors
    // ******************************

    public String get(FrameType key) {
        return data.get(key);
    }

    @Override
    public String get(String key) {
        FrameType type = FrameType.fromString(key);
        if(type == null)
            return null;

        return get(type);
    }

}
