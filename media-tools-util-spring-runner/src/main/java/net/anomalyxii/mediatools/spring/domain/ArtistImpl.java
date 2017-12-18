package net.anomalyxii.mediatools.spring.domain;

import net.anomalyxii.mediatools.api.models.Artist;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Implementation of {@link Artist} interface
 * with JPA annotations.
 * <p>
 * Created by Anomaly on 18/02/2017.
 */
@Entity(name = "artist")
public class ArtistImpl implements Artist<Long> {

    // ******************************
    // Members
    // ******************************

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    // ******************************
    // Constructors
    // ******************************

    public ArtistImpl() {
    }

    public ArtistImpl(String name) {
        this.name = name;
    }

    public ArtistImpl(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // ******************************
    // Getters & Setters
    // ******************************

    @Override
    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
