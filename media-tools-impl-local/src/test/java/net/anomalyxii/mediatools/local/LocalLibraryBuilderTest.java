package net.anomalyxii.mediatools.local;

import net.anomalyxii.mediatools.api.builders.LibraryBuilder;
import net.anomalyxii.mediatools.api.models.Library;

import java.net.URI;

import static org.junit.Assert.*;

/**
 * Created by Anomaly on 16/04/2016.
 */
public class LocalLibraryBuilderTest {

    // ******************************
    // Test Methods
    // ******************************

    //@Test
    public void load() throws Exception {

        // arrange
        URI uri = URI.create("file:////ISHIKAWA/Music");
        LibraryBuilder loader = new LocalLibraryBuilder();

        // act
        Library library = loader.build(uri);

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