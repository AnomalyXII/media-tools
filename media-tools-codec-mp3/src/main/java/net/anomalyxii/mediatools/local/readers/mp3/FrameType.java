package net.anomalyxii.mediatools.local.readers.mp3;

/**
 * Created by Anomaly on 24/04/2016.
 */
enum FrameType {

    // ID3v2 03.00
    AENC(""),
    APIC(""),
    COMM(""),
    COMR(""),
    ENCR(""),
    @Deprecated
    EQUA(""),
    ETCO(""),
    GEOB(""),
    GRID(""),
    @Deprecated
    IPLS(""),
    LINK(""),
    MCDI(""),
    MLLT(""),
    OWNE(""),
    PRIV(""),
    PCNT(""),
    POPM(""),
    POSS(""),
    RBUF(""),
    @Deprecated
    RVAD(""),
    RVRB(""),
    SYLT(""),
    SYTC(""),
    TALB("ALBUM", "ALBUM TITLE", "MOVIE", "MOVIE TITLE", "SHOW", "SHOW TITLE"),
    TBPM("BPM", "BEATS PER MINUTE"),
    TCOM("COMPOSERS"),
    TCON("CONTENT TYPE"),
    TCOP("COPYRIGHT", "COPYRIGHT MESSAGE"),
    @Deprecated
    TDAT("DATE", "RECORDING DATE"),
    TDLY("PLAYLIST DELAY"),
    TENC("ENCODED BY"),
    TEXT("LYRICISTS", "WRITERS", "TEXT WRITERS"),
    TFLT("FILE TYPE"),
    @Deprecated
    TIME("TIME"),
    TIT1("CONTENT GROUP DESCRIPTION"),
    TIT2("TITLE", "SONG NAME", "SONGNAME", "CONTENT DESCRIPTION"),
    TIT3("SUBTITLE", "DESCRIPTION"),
    TKEY("INITIAL KEY"),
    TLAN("LANGUAGES"),
    TLEN("LENGTH"),
    TMED("MEDIA TYPE"),
    TOAL("ORIGINAL ALBUM", "ORIGINAL MOVIE", "ORIGINAL SHOW"),
    TOFN("ORIGINAL FILENAME"),
    TOLY("ORIGINAL LYRICISTS", "ORIGINAL WRITERS", "ORIGINAL TEXT WRITERS"),
    TOPE("ORIGINAL ARTISTS", "ORIGINAL PERFORMERS"),
    @Deprecated
    TORY("ORIGINAL RELEASE YEAR"),
    TOWN("LICENSEE", "FILE OWNER"),
    TPE1("ARTIST", "LEAD ARTIST", "PERFORMERS"),
    TPE2("ALBUM ARTIST", "BAND", "ORCHESTRA", "ACCOMPANIMENT"),
    TPE3("CONDUCTOR"),
    TPE4("INTERPRETED"),
    TPOS("PART OF A SET"),
    TPUB("PUBLISHER"),
    TRCK("TRACK NUMBER", "POSITION IN SET"),
    @Deprecated
    TRDA("RECORDING DATES"),
    TRSN("INTERNET RADIO STATION NAME"),
    TRSO("INTERNET RADIO STATION OWNER"),
    @Deprecated
    TSIZ("SIZE"),
    TSRC("ISRC"),
    TSSE("ENCODING SETTINGS"),
    @Deprecated
    TYER("YEAR"),
    TXXX("USER DEFINED"),
    UFID("UNIQUE FILE IDENTIFIER"),
    USER(""),
    USLT(""),
    WCOM("COMMERCIAL INFORMATION URL"),
    WCOP("COPYRIGHT URL"),
    WOAF("OFFICIAL FILE URL"),
    WOAR("OFFICIAL ARTIST URL"),
    WOAS("OFFICIAL AUDIO SOURCE URL"),
    WORS("OFFICIAL INTERNET RADIO STATION URL"),
    WPAY("PAYMENT URL"),
    WPUB("PUBLISHER URL"),
    WXXX("USER DEFINED URL"),

    // ID3v2 04.00
    ASPI("Audio Seek Point Index"),
    EQU2("Equalisation", "Equalisation (2)"),
    RVA2("Relative Volume Adjustment", "Relative Volume Adjustment (2)"),
    SEEK("Seek Frame"),
    SIGN("Signature"),
    TDEN("Encoding Time"),
    TDOR("Original Release Time"),
    TDRC("Recording Time"),
    TDRL("Release Time"),
    TDTG("Tagging Time"),
    TIPL("Involved People List"),
    TMCL("Musician Credits List"),
    TMOO("Mood"),
    TPRO("Produced Notice"),
    TSOA("Album Sort Order"),
    TSOP("Performer Sort Order"),
    TSOT("Title Sort Order"),
    TSST("Set Subtitle"),

    // End
    ;

    private final String key;
    private final String[] otherKeys;
    FrameType(String key, String... other) {
        this.key = key;
        this.otherKeys = other;
    }

    public static FrameType fromString(String key) {
        try {
            return valueOf(key.toUpperCase());
        } catch(Exception e){
            for (FrameType type : values()) {
                if (type.key.equalsIgnoreCase(key))
                    return type;
            }
        }

        return null;
    }
}
