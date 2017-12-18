package net.anomalyxii.mediatools.local.readers.mp3;

import net.anomalyxii.mediatools.api.readers.MetadataReader;
import net.anomalyxii.mediatools.local.readers.mp3.Mp3AudioFileReader.MetadataVersion;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by Anomaly on 21/04/2016.
 */
public class Id3v2MetadataReader implements MetadataReader {

    public static final Id3v2MetadataReader INSTANCE_NON_STRICT = new Id3v2MetadataReader();
    public static final Id3v2MetadataReader INSTANCE_STRICT_03_00 = new Id3v2MetadataReader(MetadataVersion.Id3v2_0300);
    public static final Id3v2MetadataReader INSTANCE_STRICT_04_00 = new Id3v2MetadataReader(MetadataVersion.Id3v2_0300);
    //
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final Charset UTF_16 = Charset.forName("UTF-16");
    private static final Charset UTF_16LE = Charset.forName("UTF-16LE");
    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    // ******************************
    // Members
    // ******************************

    private final boolean isStrict;
    private final MetadataVersion supportedVersion;

    // ******************************
    // Constructors
    // ******************************

    private Id3v2MetadataReader() {
        this.isStrict = false;
        this.supportedVersion = null;
    }

    private Id3v2MetadataReader(MetadataVersion supportedVersion) {
        this.isStrict = true;
        this.supportedVersion = supportedVersion;
    }

    // ******************************
    // AbstractMetadataReader Methods
    // ******************************

    @Override
    public Mp3Metadata read(URI uri) throws IOException {

        try (InputStream in = uri.toURL().openStream()) {

            byte[] header = new byte[10];
            int read = read(in, header);
            if (read == -1)
                throw new IOException("Could not read ID3v2 header - unexpected EOF");

            if (header[0] != 'I' && header[1] == 'D' && header[2] == '3')
                throw new IOException("Could not read ID3v2 header");

            MetadataVersion version = header[3] == 0x03 && header[4] == 0x00
                                      ? MetadataVersion.Id3v2_0300
                                      : header[3] == 0x04 && header[4] == 0x00
                                        ? MetadataVersion.Id3v2_0400
                                        : null;

            if (version == null || isStrict && version != supportedVersion)
                throw new IOException("Could not read ID3v2 header - unsupported ID3v2 version");

            if (version == MetadataVersion.Id3v2_0300)
                return readMetadataV3(in, header);
            else
                return readMetadataV4(in, header);

        }

    }

    // ******************************
    // Helper Methods
    // ******************************

    protected int read(InputStream in, byte[] bytes) throws IOException {
        return read(in, bytes, false);
    }

    protected int read(InputStream in, byte[] bytes, boolean unsynchronisation) throws IOException {

        int read = 0;
        while (read < bytes.length) {
            int thisRead = in.read(bytes, read, bytes.length - read);
            if (thisRead == -1)
                return -1;

            read += thisRead;
        }

        if (unsynchronisation) {

            for (int i = 0; i < bytes.length - 1; i++)
                if ((0x00FF & bytes[i]) == 0x00FF) {
                    if (bytes[i + 1] == 0x00) {
                        if (i + 2 < bytes.length)
                            System.arraycopy(bytes, i + 2, bytes, i + 1, bytes.length - (i + 2));
                        bytes[bytes.length - 1] = (byte) (0x00FF & in.read());
                        read++;
                    }
                }

        }

        return read;

    }

    protected Mp3Metadata readMetadataV3(InputStream in, byte[] header) throws IOException {

        boolean unsynchronisationFlag = (header[5] & 0x80) == 0x80;
        boolean extendedHeaderFlag = (header[5] & 0x40) == 0x40;
        boolean experimentalIndicatorFlag = (header[5] & 0x20) == 0x20;

        if ((header[5] & 0x1F) != 0)
            throw new IOException("Could not read ID3v2 header - undefined flag was set!");

        int sizeOfHeader = 0;
        for (int i = 0; i < 4; i++)
            sizeOfHeader = (sizeOfHeader << 7) | (0x00FF & header[6 + i]);

        if (extendedHeaderFlag)
            readExtendedHeaderV3(in, unsynchronisationFlag);

        int read;
        int totalRead = 0;
        Map<FrameType, String> metadata = new EnumMap<>(FrameType.class);
        while (totalRead < sizeOfHeader) {

            byte[] frameHeader = new byte[10];
            read = read(in, frameHeader, unsynchronisationFlag);
            if (read == -1)
                throw new IOException("Could not read ID3v2 frame - unexpected EOF");

            totalRead += read;

            String frameId = new String(frameHeader, 0, 4);
            FrameType type = FrameType.valueOf(frameId);
            System.out.print(type);
            System.out.print(" -> ");

            int sizeOfFrame = 0;
            for (int i = 0; i < 4; i++)
                sizeOfFrame = (sizeOfFrame << 8) | (0x00FF & frameHeader[4 + i]);

            boolean tagAlterPreservationFlag = (frameHeader[8] & 0x80) == 0x80;
            boolean fileAlterPreservationFlag = (frameHeader[8] & 0x40) == 0x40;
            boolean readOnlyFlag = (frameHeader[8] & 0x20) == 0x20;
            boolean compressedFlag = (frameHeader[9] & 0x80) == 0x80;
            boolean encryptionFlag = (frameHeader[9] & 0x40) == 0x40;
            boolean groupingIdentifyFlag = (frameHeader[9] & 0x20) == 0x20;

            byte[] frameContent = new byte[sizeOfFrame];
            read = read(in, frameContent, unsynchronisationFlag);
            if (read == -1)
                throw new IOException("Could not read ID3v2 frame - unexpected EOF");

            totalRead += read;

            String content = parseContentV3(type, frameContent);
            System.out.println(content);
            metadata.put(type, content);

        }

        return new Mp3Metadata(metadata);

    }

    protected Object readExtendedHeaderV3(InputStream in, boolean unsynchronised) throws IOException {
        byte[] extendedHeader = new byte[10];
        int read = read(in, extendedHeader, unsynchronised);
        if (read == -1)
            throw new IOException("Could not read ID3v2 extended header - unexpected EOF");

        int sizeOfExtendedHeader = 0;
        for (int i = 0; i < 4; i++)
            sizeOfExtendedHeader = (sizeOfExtendedHeader << 7) | (0x00FF & extendedHeader[i]);

        boolean crcFlag = (extendedHeader[4] & 0x80) == 0x80;
        int sizeOfPadding = 0;
        for (int i = 0; i < 4; i++)
            sizeOfPadding = (sizeOfPadding << 8) | (0x00FF & extendedHeader[6 + i]);

        // Todo: do something with this!
        if (crcFlag) {
            byte[] crc = new byte[4];
            read = read(in, crc, unsynchronised);
            if (read == -1)
                throw new IOException("Could not read ID3v2 CRC - unexpected EOF");
        }

        return null;
    }

    protected Mp3Metadata readMetadataV4(InputStream in, byte[] header) throws IOException {

        boolean unsynchronisationFlag = (header[5] & 0x80) == 0x80;
        boolean extendedHeaderFlag = (header[5] & 0x40) == 0x40;
        boolean experimentalIndicatorFlag = (header[5] & 0x20) == 0x20;
        boolean footerPresent = (header[5] & 0x10) == 0x10;

        if ((header[5] & 0x0F) != 0)
            throw new IOException("Could not read ID3v2 header - undefined flag was set!");

        int sizeOfHeader = 0;
        for (int i = 0; i < 4; i++)
            sizeOfHeader = (sizeOfHeader << 7) | (0x00FF & header[6 + i]);

        if (extendedHeaderFlag)
            readExtendedHeaderV4(in, unsynchronisationFlag);

        int read;
        int totalRead = 0;
        Map<FrameType, String> metadata = new EnumMap<>(FrameType.class);
        while (totalRead < sizeOfHeader) {

            byte[] frameHeader = new byte[10];
            read = read(in, frameHeader, unsynchronisationFlag);
            if (read == -1)
                throw new IOException("Could not read ID3v2 frame - unexpected EOF");

            totalRead += read;

            String frameId = new String(frameHeader, 0, 4);
            if ("\0\0\0\0".equals(frameId)) {
                // Assume that this is all padding from now on????
                int remaining = sizeOfHeader - totalRead;
                read = read(in, new byte[remaining], unsynchronisationFlag);
                if (read == -1)
                    throw new IOException("Could not read ID3v2 padding - unexpected EOF");
                totalRead += read;
                break;
            }

            FrameType type = FrameType.valueOf(frameId);
            System.out.print(type);
            System.out.print(" -> ");

            int sizeOfFrame = 0;
            for (int i = 0; i < 4; i++)
                sizeOfFrame = (sizeOfFrame << 8) | (0x00FF & frameHeader[4 + i]);

            boolean tagAlterPreservationFlag = (frameHeader[8] & 0x80) == 0x80;
            boolean fileAlterPreservationFlag = (frameHeader[8] & 0x40) == 0x40;
            boolean readOnlyFlag = (frameHeader[8] & 0x20) == 0x20;
            boolean compressedFlag = (frameHeader[9] & 0x80) == 0x80;
            boolean encryptionFlag = (frameHeader[9] & 0x40) == 0x40;
            boolean groupingIdentifyFlag = (frameHeader[9] & 0x20) == 0x20;

            byte[] frameContent = new byte[sizeOfFrame];
            read = read(in, frameContent, unsynchronisationFlag);
            if (read == -1)
                throw new IOException("Could not read ID3v2 frame - unexpected EOF");

            String content = parseContentV4(type, frameContent);
            System.out.println(content);
            metadata.put(type, content);
            totalRead += read;

        }

        if (footerPresent) {
            byte[] footer = new byte[10];
            read = read(in, footer);
            if (read == -1)
                throw new IOException("Could not read ID3v2 footer - unexpected EOF");

            if (footer[2] != 'I' && footer[1] == 'D' && footer[0] == '3')
                throw new IOException("Could not read ID3v2 footer");

            // Todo: bother reading more of this?
        }

        return new Mp3Metadata(metadata);

    }

    protected Object readExtendedHeaderV4(InputStream in, boolean unsynchronised) throws IOException {
        byte[] extendedHeader = new byte[6];
        int read = read(in, extendedHeader, unsynchronised);
        if (read == -1)
            throw new IOException("Could not read ID3v2 extended header - unexpected EOF");

        int sizeOfExtendedHeader = 0;
        for (int i = 0; i < 4; i++)
            sizeOfExtendedHeader = (sizeOfExtendedHeader << 7) | (0x00FF & extendedHeader[i]);

        int numberOfFlagBytes = extendedHeader[4];
        boolean isUpdateTagFlag = (extendedHeader[5] & 0x40) == 0x40;
        boolean crcDataPresentFlag = (extendedHeader[5] & 0x20) == 0x20;
        boolean tagRestrictionsFlag = (extendedHeader[5] & 0x10) == 0x10;

        if ((extendedHeader[5] & (0x8F)) != 0)
            throw new IOException("Could not read ID3v2 extended header - undefined flag was set!");

        if (crcDataPresentFlag) {
            byte[] crc = new byte[5];
            read = read(in, crc, unsynchronised);
            if (read == -1)
                throw new IOException("Could not read ID3v2 CRC - unexpected EOF");
        }

        if (tagRestrictionsFlag) {
            byte[] restrictions = new byte[1];
            read = read(in, restrictions, unsynchronised);
            if (read == -1)
                throw new IOException("Could not read ID3v2 CRC - unexpected EOF");
        }

        return null;
    }

    protected String parseContentV3(FrameType type, byte[] content) throws UnsupportedEncodingException {

        switch (type) {

            // Unique File ID
            case UFID:
                return null;

            case USER:
            case USLT:
                return null;

            case WCOM:
            case WCOP:
            case WOAF:
            case WOAR:
            case WOAS:
            case WORS:
            case WPAY:
            case WPUB:
                return new String(content, 0, content.length - 1);
            case WXXX:
                return null;

            // A "TEXT" Frame
            case TALB:
            case TBPM:
            case TCOM:
            case TCON:
            case TCOP:
            case TDAT:
            case TDLY:
            case TENC:
            case TEXT:
            case TFLT:
            case TIME:
            case TIT1:
            case TIT2:
            case TIT3:
            case TKEY:
            case TLAN:
            case TLEN:
            case TMED:
            case TOAL:
            case TOFN:
            case TOLY:
            case TOPE:
            case TORY:
            case TOWN:
            case TPE1:
            case TPE2:
            case TPE3:
            case TPE4:
            case TPOS:
            case TPUB:
            case TRCK:
            case TRDA:
            case TRSN:
            case TRSO:
            case TSIZ:
            case TSRC:
            case TSSE:
            case TYER:
                return readEncodedString(content);

            case TXXX:
                return null;

        }

        return null;
    }

    protected String parseContentV4(FrameType type, byte[] content) throws UnsupportedEncodingException {

        switch (type) {
            case ASPI:
            case EQU2:
            case RVA2:
            case SEEK:
            case SIGN:
            case TDEN:
            case TDOR:
            case TDRC:
            case TDRL:
            case TDTG:
            case TIPL:
            case TMCL:
            case TMOO:
            case TPRO:
            case TSOA:
            case TSOP:
            case TSOT:
            case TSST:
                return null;
            
            default:
                return parseContentV3(type, content);
        }

    }

    protected String readEncodedString(byte[] content) throws UnsupportedEncodingException {
        byte encoding = content[0];
        switch (encoding) {

            // ISO-8859-1 [ISO-8859-1]. Terminated with $00.
            case 0:
                return new String(content, 1, content.length - 1, ISO_8859_1);

            // UTF-16 [UTF-16] encoded Unicode [UNICODE] with BOM. All
            // strings in the same frame SHALL have the same byteorder
            // Terminated with $00 00.
            case 1:
                boolean bigEndian = (0xFF & content[1]) == 0xFE && (0xFF & content[2]) == 0xFF;
                return new String(content, 3, content.length - 3, bigEndian ? UTF_16 : UTF_16LE);

            // UTF-16BE [UTF-16] encoded Unicode [UNICODE] without BOM.
            // Terminated with $00 00.
            case 2:
                return new String(content, 1, content.length - 1, UTF_16);

            // UTF-8 [UTF-8] encoded Unicode [UNICODE]. Terminated with $00.
            case 3:
                return new String(content, 1, content.length - 1, UTF_8);
        }

        throw new UnsupportedEncodingException("Illegal ID3 Encoding type: " + encoding);
    }

    // ******************************
    // Static Methods
    // ******************************

    public static Id3v2MetadataReader getInstance() {
        return INSTANCE_NON_STRICT;
    }

    public static Id3v2MetadataReader getStrictV3Instance() {
        return INSTANCE_STRICT_03_00;
    }

    public static Id3v2MetadataReader getStrictV4Instance() {
        return INSTANCE_STRICT_04_00;
    }

}
