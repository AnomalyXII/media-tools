package net.anomalyxii.mediatools.plex;

import net.anomalyxii.mediatools.api.Album;
import net.anomalyxii.mediatools.api.Artist;
import net.anomalyxii.mediatools.api.Library;
import net.anomalyxii.mediatools.api.Song;

import java.util.List;

public class PlexLibrary implements Library<String> {

    // *********************************
    // Members
    // *********************************

    // *********************************
    // Constructors
    // *********************************

    // *********************************
    // Library Methods
    // *********************************

    @Override
    public List<Artist<String>> artists() {
        return null;
    }

    @Override
    public List<Album<String>> albums() {
        return null;
    }

    @Override
    public List<Song<String>> songs() {
        return null;
    }

}
