package net.anomalyxii.mediatools.api.builders;

import net.anomalyxii.mediatools.api.models.Library;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.Path;

/**
 * Load a {@link Library} from the given {@link URI}.
 * <p>
 * Created by Anomaly on 15/04/2016.
 */
public interface LibraryBuilder<T extends Serializable> {

    // ******************************
    // Builder Methods
    // ******************************

    /**
     * Add a {@link LibraryBuilderListener listener}.
     *
     * @param listener the {@link LibraryBuilderListener} to add
     * @return the {@link LibraryBuilder} for chaining
     */
    LibraryBuilder<T> withListener(LibraryBuilderListener<T> listener);

    /**
     * Add multiple {@link LibraryBuilderListener listeners}.
     *
     * @param listeners the {@link LibraryBuilderListener LibraryBuilderListeners} to add
     * @return the {@link LibraryBuilder} for chaining
     */
    LibraryBuilder<T> withListeners(LibraryBuilderListener<T>... listeners);

    /**
     * Add a new {@link URI}-based source.
     *
     * @param source the {@link URI} of the source
     * @return the {@link LibraryBuilder} for chaining
     */
    LibraryBuilder<T> withSource(URI source);

    /**
     * Add a new {@link Path}-based source.
     *
     * @param source the {@link Path} of the source
     * @return the {@link LibraryBuilder} for chaining
     */
    LibraryBuilder<T> withSource(Path source);

    // ******************************
    // Build Methods
    // ******************************

    /**
     * Build a new {@link Library}.
     *
     * @return the new {@link Library}
     * @throws Exception if a <b>fatal</b> error occurs during building
     */
    Library<T> build() throws Exception;

}
