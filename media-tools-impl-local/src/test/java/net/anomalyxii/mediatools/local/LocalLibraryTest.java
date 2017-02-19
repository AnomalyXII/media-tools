package net.anomalyxii.mediatools.local;

import net.anomalyxii.mediatools.api.Album;
import net.anomalyxii.mediatools.api.Artist;
import net.anomalyxii.mediatools.api.Song;
import net.anomalyxii.mediatools.api.builders.LibraryContext;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class LocalLibraryTest {

    @Mock private LibraryContext<Long> mockedLibraryContext;

    // ******************************
    // Set-up & Tear-down
    // ******************************

    @BeforeClass
    public void beforeClass() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void beforeMethod() {
        reset(mockedLibraryContext);
    }

    // ******************************
    // Test Methods
    // ******************************

    @Test
    public void artists_noArtistsFound() {
        // arrange
        when(mockedLibraryContext.getArtists()).thenReturn(Collections.emptyList());

        LocalLibrary library = new LocalLibrary(mockedLibraryContext);

        // act
        List<Artist<Long>> artists = library.artists();

        // assert
        assertNotNull(artists);
        assertTrue(artists.isEmpty());
    }

    @Test
    public void artists_artistsFound() {
        // arrange
        LocalArtist artist = new LocalArtist(1L, "TestArtist");
        when(mockedLibraryContext.getArtists()).thenReturn(Collections.singletonList(artist));

        LocalLibrary library = new LocalLibrary(mockedLibraryContext);

        // act
        List<Artist<Long>> artists = library.artists();

        // assert
        assertNotNull(artists);
        assertFalse(artists.isEmpty());
    }

    @Test
    public void albums_noAlbumsFound() {
        // arrange
        when(mockedLibraryContext.getAlbums()).thenReturn(Collections.emptyList());

        LocalLibrary library = new LocalLibrary(mockedLibraryContext);

        // act
        List<Album<Long>> albums = library.albums();

        // assert
        assertNotNull(albums);
        assertTrue(albums.isEmpty());
    }

    @Test
    public void albums_albumsFound() {
        // arrange
        LocalArtist artist = new LocalArtist(1L, "TestArtist");
        LocalAlbum album = new LocalAlbum(1L, "TestAlbum", artist);
        when(mockedLibraryContext.getAlbums()).thenReturn(Collections.singletonList(album));

        LocalLibrary library = new LocalLibrary(mockedLibraryContext);

        // act
        List<Album<Long>> albums = library.albums();

        // assert
        assertNotNull(albums);
        assertFalse(albums.isEmpty());
    }

    @Test
    public void songs_noSongsFound() {
        // arrange
        when(mockedLibraryContext.getSongs()).thenReturn(Collections.emptyList());

        LocalLibrary library = new LocalLibrary(mockedLibraryContext);

        // act
        List<Song<Long>> songs = library.songs();

        // assert
        assertNotNull(songs);
        assertTrue(songs.isEmpty());
    }

    @Test
    public void songs_songsFound() {
        // arrange
        LocalArtist artist = new LocalArtist(1L, "TestArtist");
        LocalAlbum album = new LocalAlbum(1L, "TestAlbum", artist);
        LocalSong song = new LocalSong(1L, 1, "TestSong", artist, album, null);
        when(mockedLibraryContext.getSongs()).thenReturn(Collections.singletonList(song));

        LocalLibrary library = new LocalLibrary(mockedLibraryContext);

        // act
        List<Song<Long>> songs = library.songs();

        // assert
        assertNotNull(songs);
        assertFalse(songs.isEmpty());
    }

}