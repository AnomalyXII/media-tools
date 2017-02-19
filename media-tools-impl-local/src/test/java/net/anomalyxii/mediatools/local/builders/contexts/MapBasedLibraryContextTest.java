package net.anomalyxii.mediatools.local.builders.contexts;

import net.anomalyxii.mediatools.api.Album;
import net.anomalyxii.mediatools.api.Artist;
import net.anomalyxii.mediatools.api.Song;
import net.anomalyxii.mediatools.api.exceptions.LibraryException;
import net.anomalyxii.mediatools.local.LocalAlbum;
import net.anomalyxii.mediatools.local.LocalArtist;
import net.anomalyxii.mediatools.local.LocalSong;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class MapBasedLibraryContextTest {

    // *********************************
    // Tests
    // *********************************

    // Artist

    @Test
    public void getArtists_noArtistsFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        // act
        List<Artist<Long>> artists = context.getArtists();

        // assert
        assertNotNull(artists);
        assertTrue(artists.isEmpty());
    }

    @Test
    public void getArtists_artistsFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        Artist<Long> artist = new LocalArtist(1L, "TestArtist");
        context.save(artist);

        // act
        List<Artist<Long>> artists = context.getArtists();

        // assert
        assertNotNull(artists);
        assertFalse(artists.isEmpty());
        assertEquals(artists.size(), 1);
    }

    @Test(expectedExceptions = LibraryException.class)
    public void getArtist_artistNotFound() throws LibraryException {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        // act
        context.getArtist(1L);

        // assert
        fail("Should have thrown an exception!");
    }

    @Test
    public void getArtist_artistFound() throws LibraryException {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        Artist<Long> artist = new LocalArtist(1L, "TestArtist");
        context.save(artist);

        // act
        Artist<Long> found = context.getArtist(1L);

        // assert
        assertNotNull(found);
        assertEquals((Long) 1L, found.getId());
        assertEquals("TestArtist", found.getName());
    }

    @Test(expectedExceptions = LibraryException.class)
    public void getArtistByName_artistNotFound() throws LibraryException {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        // act
        context.getArtistByName("TestArtist");

        // assert
        fail("Should have thrown an exception");
    }

    @Test
    public void getArtistByName_artistFound() throws LibraryException {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        Artist<Long> artist = new LocalArtist(1L, "TestArtist");
        context.save(artist);

        // act
        Artist<Long> found = context.getArtistByName("TestArtist");

        // assert
        assertNotNull(found);
        assertEquals((Long) 1L, found.getId());
        assertEquals("TestArtist", found.getName());
    }

    @Test
    public void findArtist_artistNotFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        // act
        Optional<Artist<Long>> found = context.findArtist(1L);

        // assert
        assertNotNull(found);
        assertFalse(found.isPresent());
    }

    @Test
    public void findArtist_artistFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        Artist<Long> artist = new LocalArtist(1L, "TestArtist");
        context.save(artist);

        // act
        Optional<Artist<Long>> found = context.findArtist(1L);

        // assert
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals((Long) 1L, found.get().getId());
        assertEquals("TestArtist", found.get().getName());
    }

    @Test
    public void findArtistByName_artistNotFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        // act
        Optional<Artist<Long>> found = context.findArtistByName("TestArtist");

        // assert
        assertNotNull(found);
        assertFalse(found.isPresent());
    }

    @Test
    public void findArtistByName_artistFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        Artist<Long> artist = new LocalArtist(1L, "TestArtist");
        context.save(artist);

        // act
        Optional<Artist<Long>> found = context.findArtistByName("TestArtist");

        // assert
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals((Long) 1L, found.get().getId());
        assertEquals("TestArtist", found.get().getName());
    }

    // Album

    @Test
    public void getAlbums_noAlbumsFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        // act
        List<Album<Long>> albums = context.getAlbums();

        // assert
        assertNotNull(albums);
        assertTrue(albums.isEmpty());
    }

    @Test
    public void getAlbums_albumsFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        LocalArtist artist = new LocalArtist(1L, "TestArtist");
        Album<Long> album = new LocalAlbum(1L, "TestAlbum", artist);
        context.save(album);

        // act
        List<Album<Long>> albums = context.getAlbums();

        // assert
        assertNotNull(albums);
        assertFalse(albums.isEmpty());
        assertEquals(albums.size(), 1);
    }

    @Test(expectedExceptions = LibraryException.class)
    public void getAlbum_albumNotFound() throws LibraryException {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        // act
        context.getAlbum(1L);

        // assert
        fail("Should have thrown an exception!");
    }

    @Test
    public void getAlbum_albumFound() throws LibraryException {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        LocalArtist artist = new LocalArtist(1L, "TestArtist");
        Album<Long> album = new LocalAlbum(1L, "TestAlbum", artist);
        context.save(album);

        // act
        Album<Long> found = context.getAlbum(1L);

        // assert
        assertNotNull(found);
        assertEquals((Long) 1L, found.getId());
        assertEquals("TestAlbum", found.getTitle());
    }

    @Test(expectedExceptions = LibraryException.class)
    public void getAlbumByArtistAndTitle_albumNotFound() throws LibraryException {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();
        LocalArtist artist = new LocalArtist(1L, "TestArtist");

        // act
        context.getAlbumByArtistAndTitle(artist, "TestAlbum");

        // assert
        fail("Should have thrown an exception");
    }

    @Test
    public void getAlbumByArtistAndTitle_albumFound() throws LibraryException {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        LocalArtist artist = new LocalArtist(1L, "TestArtist");
        Album<Long> album = new LocalAlbum(1L, "TestAlbum", artist);
        context.save(album);

        // act
        Album<Long> found = context.getAlbumByArtistAndTitle(artist, "TestAlbum");

        // assert
        assertNotNull(found);
        assertEquals((Long) 1L, found.getId());
        assertEquals("TestAlbum", found.getTitle());
    }

    @Test
    public void findAlbum_albumNotFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        // act
        Optional<Album<Long>> album = context.findAlbum(1L);

        // assert
        assertNotNull(album);
        assertFalse(album.isPresent());
    }

    @Test
    public void findAlbum_albumFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        LocalArtist artist = new LocalArtist(1L, "TestArtist");
        Album<Long> album = new LocalAlbum(1L, "TestAlbum", artist);
        context.save(album);

        // act
        Optional<Album<Long>> found = context.findAlbum(1L);

        // assert
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals((Long) 1L, found.get().getId());
        assertEquals("TestAlbum", found.get().getTitle());
    }

    @Test
    public void findAlbumByArtistAndTitle_albumNotFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();
        LocalArtist artist = new LocalArtist(1L, "TestArtist");

        // act
        Optional<Album<Long>> found = context.findAlbumByArtistAndTitle(artist, "TestAlbum");

        // assert
        assertNotNull(found);
        assertFalse(found.isPresent());
    }

    @Test
    public void findAlbumByArtistAndTitle_albumFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        LocalArtist artist = new LocalArtist(1L, "TestArtist");
        Album<Long> album = new LocalAlbum(1L, "TestAlbum", artist);
        context.save(album);

        // act
        Optional<Album<Long>> found = context.findAlbumByArtistAndTitle(artist, "TestAlbum");

        // assert
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals((Long) 1L, found.get().getId());
        assertEquals("TestAlbum", found.get().getTitle());
    }

    // Song

    @Test
    public void getSongs_noSongsFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        // act
        List<Song<Long>> songs = context.getSongs();

        // assert
        assertNotNull(songs);
        assertTrue(songs.isEmpty());
    }

    @Test
    public void getSongs_songsFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        LocalArtist artist = new LocalArtist(1L, "TestArtist");
        LocalAlbum album = new LocalAlbum(1L, "TestAlbum", artist);
        Song<Long> song = new LocalSong(1L, 1,"TestSong", artist, album, null);
        context.save(song);

        // act
        List<Song<Long>> songs = context.getSongs();

        // assert
        assertNotNull(songs);
        assertFalse(songs.isEmpty());
        assertEquals(songs.size(), 1);
    }

    @Test(expectedExceptions = LibraryException.class)
    public void getSong_songNotFound() throws LibraryException {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        // act
        context.getSong(1L);

        // assert
        fail("Should have thrown an exception!");
    }

    @Test
    public void getSong_songFound() throws LibraryException {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        LocalArtist artist = new LocalArtist(1L, "TestArtist");
        LocalAlbum album = new LocalAlbum(1L, "TestAlbum", artist);
        Song<Long> song = new LocalSong(1L, 1,"TestSong", artist, album, null);
        context.save(song);

        // act
        Song<Long> found = context.getSong(1L);

        // assert
        assertNotNull(found);
        assertEquals((Long) 1L, found.getId());
        assertEquals("TestSong", found.getTitle());
    }

    @Test(expectedExceptions = LibraryException.class)
    public void getSongByArtistAndTitle_songNotFound() throws LibraryException {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();
        LocalArtist artist = new LocalArtist(1L, "TestArtist");
        LocalAlbum album = new LocalAlbum(1L, "TestAlbum", artist);

        // act
        context.getSongByAlbumAndTrackNumber(album, 1);

        // assert
        fail("Should have thrown an exception");
    }

    @Test
    public void getSongByArtistAndTitle_songFound() throws LibraryException {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        LocalArtist artist = new LocalArtist(1L, "TestArtist");
        LocalAlbum album = new LocalAlbum(1L, "TestAlbum", artist);
        Song<Long> song = new LocalSong(1L, 1,"TestSong", artist, album, null);
        context.save(song);

        // act
        Song<Long> found = context.getSongByAlbumAndTrackNumber(album, 1);

        // assert
        assertNotNull(found);
        assertEquals((Long) 1L, found.getId());
        assertEquals("TestSong", found.getTitle());
    }

    @Test
    public void findSong_songNotFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        // act
        Optional<Song<Long>> song = context.findSong(1L);

        // assert
        assertNotNull(song);
        assertFalse(song.isPresent());
    }

    @Test
    public void findSong_songFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        LocalArtist artist = new LocalArtist(1L, "TestArtist");
        LocalAlbum album = new LocalAlbum(1L, "TestAlbum", artist);
        Song<Long> song = new LocalSong(1L, 1,"TestSong", artist, album, null);
        context.save(song);

        // act
        Optional<Song<Long>> found = context.findSong(1L);

        // assert
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals((Long) 1L, found.get().getId());
        assertEquals("TestSong", found.get().getTitle());
    }

    @Test
    public void findSongByArtistAndTitle_songNotFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();
        LocalArtist artist = new LocalArtist(1L, "TestArtist");
        LocalAlbum album = new LocalAlbum(1L, "TestAlbum", artist);

        // act
        Optional<Song<Long>> found = context.findSongByAlbumAndTrackNumber(album, 1);

        // assert
        assertNotNull(found);
        assertFalse(found.isPresent());
    }

    @Test
    public void findSongByArtistAndTitle_songFound() {
        // arrange
        MapBasedLibraryContext<Long> context = new MapBasedLibraryContext<>();

        LocalArtist artist = new LocalArtist(1L, "TestArtist");
        LocalAlbum album = new LocalAlbum(1L, "TestAlbum", artist);
        Song<Long> song = new LocalSong(1L, 1,"TestSong", artist, album, null);
        context.save(song);

        // act
        Optional<Song<Long>> found = context.findSongByAlbumAndTrackNumber(album, 1);

        // assert
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals((Long) 1L, found.get().getId());
        assertEquals("TestSong", found.get().getTitle());
    }
    
}