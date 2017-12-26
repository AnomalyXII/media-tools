package net.anomalyxii.mediatools.plex.builders;

import net.anomalyxii.mediatools.api.builders.Source;
import net.anomalyxii.mediatools.api.exceptions.MediaException;
import net.anomalyxii.mediatools.plex.builders.sources.PlexSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

public class PlexSourceTest {

    private static final URI EXAMPLE_URI = URI.create("http://example.com/plex");

    @Mock private PlexResolver mockedPlexResolver;

    // ******************************
    // Set-up & Tear Down
    // ******************************

    @BeforeClass
    public void beforeClass() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        reset(mockedPlexResolver);
    }

    // ******************************
    // Test Methods
    // ******************************

    // Constructor

    @Test(expectedExceptions = NullPointerException.class)
    public void URI_null() {
        // arrange
        // Nothing to do! :D

        // act
        new PlexSource(null, mockedPlexResolver);

        // assert
        fail("Should have thrown an exception");
    }

    @Test
    public void URI_notNull() {
        // arrange
        // Nothing to do! :D

        // act
        PlexSource source = new PlexSource(EXAMPLE_URI, mockedPlexResolver);

        // assert
        assertEquals(source.toUri(), EXAMPLE_URI);
    }

    // isLeaf

    @Test
    public void isLeaf_alwaysFalse() {
        // arrange
        PlexSource source = new PlexSource(EXAMPLE_URI, mockedPlexResolver);

        // act
        boolean leaf = source.isLeaf();

        // assert
        assertFalse(leaf);
    }

    // expand
    
    @Test
    public void expand_validResolver() throws MediaException {
        // arrange
        List<Source> result = Collections.emptyList();
        when(mockedPlexResolver.resolveLibrary(EXAMPLE_URI)).thenReturn(result);
        
        PlexSource source = new PlexSource(EXAMPLE_URI, mockedPlexResolver);

        // act
        List<Source> expanded = source.expand();

        // assert
        assertNotNull(expanded);
        assertEquals(expanded, result);
    }

    // toUri

    @Test
    public void toUri_exactURI() {
        // arrange
        PlexSource source = new PlexSource(EXAMPLE_URI, mockedPlexResolver);

        // act
        URI uri = source.toUri();

        // assert
        assertNotNull(uri);
        assertEquals(uri, EXAMPLE_URI);
    }

    // toAudioFile

    @Test(expectedExceptions = MediaException.class)
    public void toAudioFile_mediaException() throws MediaException {
        // arrange
        PlexSource source = new PlexSource(EXAMPLE_URI, mockedPlexResolver);

        // act
        source.toAudioFile();

        // assert
        fail("Should have thrown an exception");
    }

}