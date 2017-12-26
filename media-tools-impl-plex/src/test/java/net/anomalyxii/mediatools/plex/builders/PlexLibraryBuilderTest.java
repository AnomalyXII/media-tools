package net.anomalyxii.mediatools.plex.builders;

import net.anomalyxii.mediatools.api.exceptions.MediaException;
import net.anomalyxii.mediatools.plex.PlexLibrary;
import org.testng.annotations.Test;

import java.net.URI;

public class PlexLibraryBuilderTest {

    // ******************************
    // Test Methods
    // ******************************

    @Test
    public void build_test() throws MediaException {
        // arrange
        PlexLibraryBuilder builder = new PlexLibraryBuilder()
                .withSource(URI.create("http://misao:32400/"));

        // act
        PlexLibrary library = builder.build();

        // assert
    }

}