package net.anomalyxii.mediatools.api.models;

import java.io.Serializable;
import java.util.List;

/**
 * A Library.
 * <p>
 * A {@code Library} is effectively a repository for
 * {@link Artist Artists}, {@link Album Albums} and
 * {@link Song Songs}.
 * <p>
 * Unless otherwise specified, every {@link Artist} in the
 * {@code Library} will be associated with at least one
 * {@link Album} (or {@link Song} in the case of album artists)
 * and every {@link Album} will be associated with at least one
 * {@link Song}.
 * <p>
 * Created by Anomaly on 15/04/2016.
 */
public interface Library<ID extends Serializable> {

    // ******************************
    // Interface Methods
    // ******************************

    /**
     * Return all the {@link Artist artists} that are included in
     * this {@code Library}
     *
     * @return a {@link List} of {@link Artist artists}
     */
    List<Artist<ID>> artists();

    /**
     * Return all the {@link Album albums} that are included in
     * this {@code Library}
     *
     * @return a {@link List} of {@link Album albums}
     */
    List<Album<ID>> albums();

    /**
     * Return all the {@link Song songs} that are included in
     * this {@code Library}
     *
     * @return a {@link List} of {@link Song songs}
     */
    List<Song<ID>> songs();

}
