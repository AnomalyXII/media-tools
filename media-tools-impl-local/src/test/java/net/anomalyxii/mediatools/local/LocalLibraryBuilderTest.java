package net.anomalyxii.mediatools.local;

import net.anomalyxii.mediatools.api.Library;
import net.anomalyxii.mediatools.api.builders.LibraryBuilder;
import net.anomalyxii.mediatools.local.builders.LocalLibraryBuilder;
import org.testng.annotations.Test;

import java.net.URI;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LocalLibraryBuilderTest {

    // ******************************
    // Test Methods
    // ******************************

    @Test
    public void load() throws Exception {

        // arrange
        URI uri = URI.create("file:///media/samba/music/");
        LibraryBuilder loader = new LocalLibraryBuilder().withSource(uri);

        // act
        Library library = loader.build();

        // assert
        assertNotNull(library);
        assertTrue(library.songs().size() > 0);
        assertTrue(library.albums().size() > 0);
        assertTrue(library.artists().size() > 0);

        System.out.println("Loaded " + library.songs().size() + " songs");
        System.out.println("Loaded " + library.albums().size() + " albums");
        System.out.println("Loaded " + library.artists().size() + " artists");

    }

}