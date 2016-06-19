package ua.stepiukyevhen.multiplay.data;


import android.provider.BaseColumns;

public final class Contract {

    public Contract() {}

    public static abstract class MusicEntry implements BaseColumns {
        public static final String TABLE = "music_entry";
        public static final String ID_COLUMN = "entry_id";
        public static final String TITLE_COLUMN = "title";
        public static final String ARTIST_COLUMN = "artist";
        public static final String ALBUM_COLUMN = "album";
        public static final String IMAGE_COLUMN = "image";
        public static final String LENGTH_COLUMN = "length";
        public static final String SOUNDCLOUD_TAG_COLUMN = "soundcloud";
        public static final String PATH_COLUMN = "path";
    }
}
