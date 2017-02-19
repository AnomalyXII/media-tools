package net.anomalyxii.mediatools.api.models;

import net.anomalyxii.mediatools.api.exceptions.LibraryException;

import java.io.Serializable;

/**
 * An extension of the {@link Library} interface that supports
 * modification. Specifically, {@link Library libraries} that
 * extend this interface should support new {@link Song songs}
 * being added or removed.
 * <p>
 * Created by Anomaly on 18/02/2017.
 */
public interface MutableLibrary<ID extends Serializable> extends Library<ID> {

    // ******************************
    // Interface Methods
    // ******************************

    Song<ID> add(Song<ID> song) throws LibraryException;

    Song<ID> remove(Song<ID> song) throws LibraryException;

}
