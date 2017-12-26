package net.anomalyxii.mediatools.plex.builders.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A Plex metadata for {@code /library/sections}.
 */
public class Metadata {

    // *********************************
    // Members
    // *********************************

    public String ratingKey;
    public String key;
    public String studio;
    public String type;
    public String title;
    public String titleSort;
    public String contentRating;
    public String summary;
    public double rating;
    public int ratingCount;
    public long year;
    public String tagline;
    public Integer index;
    public Integer viewCount;
    public long lastViewedAt;
    public String thumb;
    public String art;
    public long duration;
    public String originalTitle;
    public String originallyAvailableAt;
    public long addedAt;
    public long updatedAt;
    public String chapterSource;
    public Integer parentRatingKey;
    public String parentKey;
    public String parentTitle;
    public String parentThumb;
    public Integer deepAnalysisVersion;
    public String grandparentKey;
    public int grandparentRatingKey;
    public String grandparentThumb;
    public String grandparentTitle;
    public String identifier;
    public int librarySectionID;
    public String librarySectionTitle;
    public String librarySectionUUID;
    public String mediaTagPrefix;
    public long mediaTagVersion;
    public boolean nocache;
    public int parentIndex;
    public int parentYear;
    public String title1;
    public String title2;
    public String viewGroup;
    public int viewMode;

    @JsonProperty("Genre")
    public List<Tag> genre;
    @JsonProperty("Directory")
    public List<Tag> directory;
    @JsonProperty("Writer")
    public List<Tag> writer;
    @JsonProperty("Country")
    public List<Tag> country;
    @JsonProperty("Role")
    public List<Tag> role;
    @JsonProperty("Similar")
    public List<Tag> similar;
    @JsonProperty("Media")
    public List<Media> media;

}
