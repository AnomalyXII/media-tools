package net.anomalyxii.mediatools.spring.data.repositories;

import net.anomalyxii.mediatools.spring.domain.SongImpl;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Anomaly on 18/02/2017.
 */
public interface SongRepository extends CrudRepository<SongImpl, Long> {

    // ******************************
    // Interface Methods
    // ******************************

    SongImpl findByFile(String file);

}
