package net.anomalyxii.mediatools.spring.domain;

import net.anomalyxii.mediatools.api.models.Album;
import net.anomalyxii.mediatools.api.models.Artist;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Implementation of the {@link Album} interface
 * with JPA annotations.
 * <p>
 * Created by Anomaly on 18/04/2016.
 */
@Entity(name = "album")
public class AlbumImpl implements Album<Long> {

    // ******************************
    // Members
    // ******************************

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(targetEntity = ArtistImpl.class)
    private Artist<Long> artist;

    // ******************************
    // Constructors
    // ******************************

    public AlbumImpl() {
    }

    public AlbumImpl(String name, Artist<Long> artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
    }

    public AlbumImpl(Long id, String name, Artist<Long> artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
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

    @Override
    public String getTitle() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ******************************
    // Foreign Key Getters & Setters
    // ******************************

    @Override
    public Artist<Long> getAlbumArtist() {
        return artist;
    }

    public void setAlbumArtist(Artist<Long> artist) {
        this.artist = artist;
    }

}
