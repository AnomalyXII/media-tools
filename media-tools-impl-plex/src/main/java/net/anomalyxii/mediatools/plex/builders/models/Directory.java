package net.anomalyxii.mediatools.plex.builders.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A PlexMedia directory that contains all the common attributes.
 */
public class Directory {

    // *********************************
    // Members
    // *********************************

    public String key;
    public String title;

    public Integer count;
    public Boolean allowSync;
    public String art;
    public String composite;
    public Boolean filters;
    public Boolean refreshing;
    public String thumb;
    public String type;
    public String agent;
    public String scanner;
    public String language;
    public String uuid;
    public long updatedAt;
    public long createdAt;

    @JsonProperty("Location")
    public List<Location> location;

    @JsonProperty("Genre")
    public List<Tag> genre;

}
