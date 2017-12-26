package net.anomalyxii.mediatools.api.builders;

import net.anomalyxii.mediatools.api.Library;
import net.anomalyxii.mediatools.api.builders.LibraryBuilder;
import net.anomalyxii.mediatools.api.builders.LibraryBuilderListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractLibraryBuilder<ID extends Serializable> implements LibraryBuilder<ID> {

    // *********************************
    // Members
    // *********************************

    private final List<LibraryBuilderListener<ID>> listeners = new ArrayList<>();

    // *********************************
    // Constructors
    // *********************************

    protected AbstractLibraryBuilder() {
    }

    // *********************************
    // LibraryBuilder Methods
    // *********************************

    @Override
    public AbstractLibraryBuilder<ID> withListener(LibraryBuilderListener<ID> listener) {
        this.listeners.add(listener);
        return this;
    }

    @Override
    public AbstractLibraryBuilder<ID> withListeners(LibraryBuilderListener<ID>... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
        return this;
    }

    // *********************************
    // Listener Methods
    // *********************************

    /**
     * Fire the {@link LibraryBuilderListener#onBuildStarted(Library)} for
     * all the listeners reigstered on this {@link LibraryBuilder}.
     *
     * @param library the {@link Library} being built
     */
    protected void fireOnBuildStarted(Library<ID> library) {
        for(LibraryBuilderListener<ID> listener : listeners) {
            listener.onBuildStarted(library);
        }
    }

    /**
     * Fire the {@link LibraryBuilderListener#onBuildFinished(Library)} for
     * all the listeners reigstered on this {@link LibraryBuilder}.
     *
     * @param library the {@link Library} being built
     */
    protected void fireOnBuildFinished(Library<ID> library) {
        for(LibraryBuilderListener<ID> listener : listeners) {
            listener.onBuildFinished(library);
        }
    }

}
