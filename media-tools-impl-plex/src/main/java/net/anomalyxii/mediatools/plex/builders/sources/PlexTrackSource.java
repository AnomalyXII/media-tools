package net.anomalyxii.mediatools.plex.builders.sources;

import net.anomalyxii.mediatools.api.AudioFile;
import net.anomalyxii.mediatools.api.builders.Source;
import net.anomalyxii.mediatools.api.exceptions.MediaException;
import net.anomalyxii.mediatools.plex.builders.PlexAudioFile;
import net.anomalyxii.mediatools.plex.builders.PlexResolver;
import net.anomalyxii.mediatools.plex.builders.models.Metadata;

import java.net.URI;
import java.util.Collections;
import java.util.List;

public class PlexTrackSource extends PlexMetadataSource {

    // *********************************
    // Private Members
    // *********************************

    private AudioFile audioFile;

    // *********************************
    // Constructors
    // *********************************

    public PlexTrackSource(URI root, Metadata metadata, PlexResolver resolver) {
        super(root, metadata, resolver);
    }

    // *********************************
    // Source Methods
    // *********************************

    @Override
    public boolean isLeaf() {
        return true;
    }

    @Override
    public List<Source> expand() {
        return Collections.singletonList(this);
    }

    @Override
    public AudioFile toAudioFile() {
        if (audioFile == null) {
            Metadata metadata = getMetadata();
            URI uri = this.toUri();
            String trackNumber = Integer.toString(metadata.index);
            String title = metadata.title;
            String albumName = metadata.parentTitle;
            String artistName = metadata.grandparentTitle;
            String albumArtistName = artistName;

            audioFile = new PlexAudioFile(uri, trackNumber, title, albumName, artistName, albumArtistName);
        }
        return audioFile;
    }

}
