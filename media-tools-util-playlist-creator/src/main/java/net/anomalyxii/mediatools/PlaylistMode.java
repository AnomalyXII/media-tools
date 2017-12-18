package net.anomalyxii.mediatools;

/**
 * Determine which files should be included
 * in a generated {@link net.anomalyxii.mediatools.api.models.Playlist}
 */
public enum PlaylistMode {

    /**
     * Playlist should be generated using the entire
     * music library
     */
    ALL,

    /**
     * Playlist should be generated using all files
     * associated with the selected albums
     */
    ALBUM,

    /**
     * Playlist should be generated using all files
     * associated with the selected artists
     */
    ARTIST,

    /**
     * Playlist should be generated using all files
     * associated with the selected genre
     */
    GENRE,

}
