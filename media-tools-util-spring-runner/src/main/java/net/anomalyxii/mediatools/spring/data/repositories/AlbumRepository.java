package net.anomalyxii.mediatools.spring.data.repositories;

import net.anomalyxii.mediatools.api.models.Artist;
import net.anomalyxii.mediatools.spring.domain.AlbumImpl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link CrudRepository} for {@link AlbumImpl ALbums}
 */
@Repository
public interface AlbumRepository extends CrudRepository<AlbumImpl, Long> {

    // ******************************
    // Interface Methods
    // ******************************

    AlbumImpl findByArtistAndName(Artist<Long> artist, String name);

}
