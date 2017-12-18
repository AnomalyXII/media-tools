package net.anomalyxii.mediatools.spring.data.repositories;

import net.anomalyxii.mediatools.spring.domain.ArtistImpl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link CrudRepository} for {@link ArtistImpl Artists}
 */
@Repository
public interface ArtistRepository extends CrudRepository<ArtistImpl, Long> {

    // ******************************
    // Interface Methods
    // ******************************

    ArtistImpl findByName(String name);

}
