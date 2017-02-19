package net.anomalyxii.mediatools.api.readers;

import net.anomalyxii.mediatools.api.Metadata;

import java.io.IOException;
import java.net.URI;

/**
 * Created by Anomaly on 17/04/2016.
 */
public interface MetadataReader {

    // ******************************
    // Interface Methods
    // ******************************

    Metadata read(URI uri) throws IOException;


}
