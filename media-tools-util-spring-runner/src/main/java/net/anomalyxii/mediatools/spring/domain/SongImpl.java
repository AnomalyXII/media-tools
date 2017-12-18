package net.anomalyxii.mediatools.spring.domain;

import net.anomalyxii.mediatools.api.models.Album;
import net.anomalyxii.mediatools.api.models.Artist;
import net.anomalyxii.mediatools.api.models.Song;

import javax.persistence.*;

/**
 * Implementation of the {@link Song} interface
 * with JPA annotations.
 * <p>
 * Created by Anomaly on 15/04/2016.
 */
@Entity(name = "song")
public class SongImpl implements Song<Long> {

    // ******************************
    // Members
    // ******************************

    @Id
    @GeneratedValue
    private Long id;
    private String file;
    private int trackNumber;
    private String title;

    @ManyToOne(targetEntity = ArtistImpl.class)
    private Artist<Long> artist;

    @ManyToOne(targetEntity = AlbumImpl.class)
    private Album<Long> album;

    // ******************************
    // Constructors
    // ******************************

    public SongImpl() {
    }

    public SongImpl(String file, int trackNumber, String title, Artist<Long> artist, Album<Long> album) {
        this(0, file, trackNumber, title, artist, album);
    }

    public SongImpl(long id, String file, int trackNumber, String title, Artist<Long> artist, Album<Long> album) {
        this.id = id;
        this.file = file;
        this.trackNumber = trackNumber;
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    // ******************************
    // Getters & Setters
    // ******************************

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNo) {
        this.trackNumber = trackNo;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // ******************************
    // Foreign Key Getters & Setters
    // ******************************

    @Override
    public Artist<Long> getArtist() {
        return artist;
    }

    public void setArtist(Artist<Long> artist) {
        this.artist = artist;
    }

    @Override
    public Album<Long> getAlbum() {
        return album;
    }

    public void setAlbum(Album<Long> album) {
        this.album = album;
    }

}
