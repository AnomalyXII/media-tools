package net.anomalyxii.mediatools.plex.builders.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.anomalyxii.mediatools.plex.builders.models.Part;

import java.util.List;

public class Media {

    // *********************************
    // Members
    // *********************************

    public String videoResolution;
    public long id;
    public long duration;
    public long bitrate;
    public long width;
    public long height;
    public double aspectRatio;
    public Integer audioChannels;
    public String audioCodec;
    public String videoCodec;
    public String container;
    public String videoFrameRate;
    public String videoProfile;
    public Integer ratingKey;
    public String key;
    public Integer parentRatingKey;
    public String type;
    public String title;
    public String parentKey;
    public String parentTitle;
    public String summary;
    public Integer index;
    public Integer year;
    public String thumb;
    public String parentThumb;
    public long originallyAvailableAt;
    public long addedAt;
    public long updatedAt;
    public Integer deepAnalysisVersion;

    @JsonProperty("Part")
    public List<Part> part;

}
