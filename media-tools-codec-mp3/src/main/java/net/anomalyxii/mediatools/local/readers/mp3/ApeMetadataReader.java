package net.anomalyxii.mediatools.local.readers.mp3;

/**
 * Created by Anomaly on 24/04/2016.
 */
public class ApeMetadataReader {

    private static final ApeMetadataReader INSTANCE = new ApeMetadataReader();

    // ******************************
    // Members
    // ******************************

    // ******************************
    // Constructors
    // ******************************

    private ApeMetadataReader() {
    }

    // ******************************
    // Static Methods
    // ******************************

    public ApeMetadataReader getInstance() {
        return INSTANCE;
    }

}
