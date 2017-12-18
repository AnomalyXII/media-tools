package net.anomalyxii.mediatools.readers.flac;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Anomaly on 17/04/2016.
 */
class FlacMetadataBlockVorbisComment extends FlacMetadataBlock {

    // ******************************
    // Members
    // ******************************

    private String vendor;
    private final List<Comment> comments = new ArrayList<>();

    // ******************************
    // Constructors
    // ******************************

    private FlacMetadataBlockVorbisComment(boolean last, int length) {
        super(last, VORBIS_COMMENT, length);
    }

    // ******************************
    // Getters
    // ******************************

    String getVendor() {
        return vendor;
    }

    List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }


    // ******************************
    // Static Read Method
    // ******************************

    static FlacMetadataBlockVorbisComment read(boolean last, int length, InputStream in) throws IOException {
        FlacMetadataBlockVorbisComment block = new FlacMetadataBlockVorbisComment(last, length);
        byte[] buffer = new byte[length];
        int read = in.read(buffer);
        if (read < length)
            throw new IOException("Could not read fLaC VORBIS_COMMENT - unexpected EOF");

        int vendorLength = ((((((0x00FF & buffer[3]) << 8)
                               | (0x00FF & buffer[2])) << 8)
                             | (0x00FF & buffer[1])) << 8)
                           | (0x00FF & buffer[0]);

        block.vendor = new String(buffer, 4, vendorLength);

        int start = 4 + vendorLength;
        int numberOfComments = ((((((0x00FF & buffer[start + 3]) << 8)
                                   | (0x00FF & buffer[start + 2])) << 8)
                                 | (0x00FF & buffer[start + 3])) << 8)
                               | (0x00FF & buffer[start]);

        start += 4;

        for (int i = 0; i < numberOfComments; i++) {

            int commentLength = ((((((0x00FF & buffer[start + 3]) << 8)
                                    | (0x00FF & buffer[start + 2])) << 8)
                                  | (0x00FF & buffer[start + 1])) << 8)
                                | (0x00FF & buffer[start]);

            String commentString = new String(buffer, start + 4, commentLength);
            int splitAt = commentString.indexOf('=');

            start += 4 + commentLength;

            Comment comment = new Comment(
                    commentString.substring(0, splitAt),
                    commentString.substring(splitAt + 1, commentString.length())
            );

            block.comments.add(comment);

        }

        return block;
    }

    // ******************************
    // Comment Class
    // ******************************

    static class Comment {

        final String key;
        final String value;

        public Comment(String key, String value) {
            this.key = key;
            this.value = value;
        }

    }

}
