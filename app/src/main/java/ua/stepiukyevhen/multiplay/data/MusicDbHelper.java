package ua.stepiukyevhen.multiplay.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import rx.Observable;
import rx.Subscriber;

import static ua.stepiukyevhen.multiplay.data.Contract.MusicEntry;
/**
 * Created by dnt on 6/15/16.
 */
public class MusicDbHelper extends SQLiteOpenHelper {

    public static final class Queries {
        public static final String SQL_CREATE_ENTRIES =
                "create table " + MusicEntry.TABLE + "(" +
                        MusicEntry.ID_COLUMN + " integer primary key," +
                        MusicEntry.TITLE_COLUMN + " text," +
                        MusicEntry.ARTIST_COLUMN + " text," +
                        MusicEntry.ALBUM_COLUMN + " text," +
                        MusicEntry.IMAGE_COLUMN + " text," +
                        MusicEntry.LENGTH_COLUMN + " integer," +
                        MusicEntry.SOUNDCLOUD_TAG_COLUMN + " boolean)";

        public static final String SQL_DROP_ENTRIES =
                "drop table if exists " + MusicEntry.TABLE;
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "db";

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

    public Observable<SQLiteDatabase> getWritableDb() {
        return Observable.create(new Observable.OnSubscribe<SQLiteDatabase>() {
            @Override
            public void call(Subscriber<? super SQLiteDatabase> subscriber) {
                subscriber.onNext(getWritableDatabase());
            }
        });
    }

    public Observable<SQLiteDatabase> getReadableDb() {
        return Observable.create(new Observable.OnSubscribe<SQLiteDatabase>() {
            @Override
            public void call(Subscriber<? super SQLiteDatabase> subscriber) {
                subscriber.onNext(getReadableDatabase());
            }
        });
    }
}
