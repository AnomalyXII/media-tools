package net.anomalyxii.mediatools.plex.builders.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A PlexMedia container that contains all the common attributes.
 */
public class MediaContainer {

    // *********************************
    // Members
    // *********************************

    public Integer key;
    public Integer size;
    public Boolean allowSync;
    public Boolean allowCameraUpload;
    public Boolean allowChannelAccess;
    public Boolean allowMediaDeletion;
    public Boolean allowSharing;
    public Boolean backgroundProcessing;
    public Boolean certificate;
    public Boolean companionProxy;
    public String countryCode;
    public String diagnostics;
    public Boolean eventStream;
    public String friendlyName;
    public Boolean hubSearch;
    public Boolean itemClusters;
    public Integer livetv;
    public String machineIdentifier;
    public Boolean mediaProviders;
    public Boolean multiuser;
    public Boolean myPlex;
    public String myPlexMappingState;
    public String myPlexSigninState;
    public Boolean myPlexSubscription;
    public String myPlexUsername;
    public String ownerFeatures;
    public Boolean photoAutoTag;
    public String platform;
    public String platformVersion;
    public Boolean pluginHost;
    public Boolean readOnlyLibraries;
    public Boolean requestParametersInCookie;
    public Integer streamingBrainABRVersion;
    public Integer streamingBrainVersion;
    public Boolean sync;
    public Integer transcoderActiveVideoSessions;
    public Boolean transcoderAudio;
    public Boolean transcoderLyrics;
    public Boolean transcoderPhoto;
    public Boolean transcoderSubtitles;
    public Boolean transcoderVideo;
    public String transcoderVideoBitrates;
    public String transcoderVideoQualities;
    public String transcoderVideoResolutions;
    public long updatedAt;
    public Boolean updater;
    public String version;
    public Boolean voiceSearch;
    public String art;
    public String identifier;
    public Integer librarySectionID;
    public String librarySectionTitle;
    public String librarySectionUUID;
    public String mediaTagPrefix;
    public long mediaTagVersion;
    public Boolean nocache;
    public Integer parentIndex;
    public String parentTitle;
    public int parentYear;
    public String summary;
    public String thumb;
    public String title1;
    public String title2;
    public String viewGroup;
    public Integer viewMode;
    public int grandparentRatingKey;
    public String grandparentThumb;
    public String grandparentTitle;

    @JsonProperty("Directory")
    public List<Directory> directory;

    @JsonProperty("Metadata")
    public List<Metadata> metadata;

}
