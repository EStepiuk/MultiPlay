package ua.stepiukyevhen.multiplay.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import rx.Observable;
import rx.Subscriber;

import static ua.stepiukyevhen.multiplay.data.Contract.MusicEntry;


public class MusicDbHelper extends SQLiteOpenHelper {

    private static final class Queries {
        static final String SQL_CREATE_ENTRIES =
                "create table " + MusicEntry.TABLE + "(" +
                        MusicEntry.ID_COLUMN + " integer primary key," +
                        MusicEntry.PATH_COLUMN + " text," +
                        MusicEntry.TITLE_COLUMN + " text," +
                        MusicEntry.ARTIST_COLUMN + " text," +
                        MusicEntry.ALBUM_COLUMN + " text," +
                        MusicEntry.IMAGE_COLUMN + " blob," +
                        MusicEntry.LENGTH_COLUMN + " integer," +
                        MusicEntry.SOUNDCLOUD_TAG_COLUMN + " boolean)";

        static final String SQL_DROP_ENTRIES =
                "drop table if exists " + MusicEntry.TABLE;
    }

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "db";

    public MusicDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Queries.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Queries.SQL_DROP_ENTRIES);
        onCreate(db);
    }
}
