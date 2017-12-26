package net.anomalyxii.mediatools.plex.builders.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Plex MediaContainer response, which contains a parameterized
 * {@link MediaContainer}.
 */
public class MediaContainerResponse {

    // *********************************
    // Members
    // *********************************

    @JsonProperty("MediaContainer")
    public MediaContainer mediaContainer;

}
