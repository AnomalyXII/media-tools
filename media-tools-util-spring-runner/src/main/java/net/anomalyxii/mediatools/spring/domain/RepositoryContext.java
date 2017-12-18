package net.anomalyxii.mediatools.spring.domain;

import net.anomalyxii.mediatools.api.builders.LibraryBuilderContext;
import net.anomalyxii.mediatools.api.models.Album;
import net.anomalyxii.mediatools.api.models.Artist;
import net.anomalyxii.mediatools.api.models.Song;
import net.anomalyxii.mediatools.spring.data.repositories.AlbumRepository;
import net.anomalyxii.mediatools.spring.data.repositories.ArtistRepository;
import net.anomalyxii.mediatools.spring.data.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Anomaly on 18/02/2017.
 */
public class RepositoryContext implements LibraryBuilderContext<Long> {

    // ******************************
    // Autowired Members
    // ******************************

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private SongRepository songRepository;

    // ******************************
    // Members
    // ******************************

    private final Map<Long, Song<Long>> songIndex = new HashMap<>();
    private final Map<Path, Long> songReverseIndexPath = new HashMap<>();
    //
    private final Map<Long, Album<Long>> albumIndex = new HashMap<>();
    private final Map<Artist<Long>, Map<String, Long>> albumReverseIndexArtistAndName = new HashMap<>();

    // ******************************
    // Getters & Setters
    // ******************************

    public Collection<Song<Long>> getSongIndex() {
        return StreamSupport.stream(songRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Collection<Artist<Long>> getArtistIndex() {
        return StreamSupport.stream(artistRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Collection<Album<Long>> getAlbumIndex() {
        return StreamSupport.stream(albumRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }


    // ******************************
    // Helper Methods
    // ******************************

    @Override
    public Artist<Long> findOrCreateArtist(String artistName) {
        ArtistImpl artist = artistRepository.findByName(artistName);
        if (artist != null)
            return artist;

        synchronized (artistRepository) {
            artist = new ArtistImpl(artistName);
            return artistRepository.save(artist);
        }
    }

    @Override
    public Album<Long> findOrCreateAlbum(String albumName, Artist<Long> artist) {
        AlbumImpl album = albumRepository.findByArtistAndName(artist, albumName);
        if (album  != null)
            return album ;

        synchronized (albumRepository) {
            album  = new AlbumImpl(albumName, artist);
            return albumRepository.save(album);
        }
    }

    @Override
    public Song<Long> findOrCreateSong(String file, int trackNo, String title, Artist<Long> artist, Album<Long> album) {
        SongImpl song = songRepository.findByFile(file);
        if(song != null)
            return song;

        synchronized (songRepository) {
            song = new SongImpl(file, trackNo, title, artist, album);
            return songRepository.save(song);
        }
    }


}
