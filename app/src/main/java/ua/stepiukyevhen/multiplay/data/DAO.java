package ua.stepiukyevhen.multiplay.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaMetadataRetriever;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import ua.stepiukyevhen.multiplay.models.Track;
import static ua.stepiukyevhen.multiplay.data.Contract.MusicEntry;


public class DAO {

    private final MusicDbHelper helper;

    public DAO(MusicDbHelper helper) {
        this.helper = helper;
    }

    public Observable<List<Track>> getTracks() {
        return Observable.create(subscriber -> {
            String[] projection = {
                    MusicEntry.ARTIST_COLUMN,
                    MusicEntry.TITLE_COLUMN,
                    MusicEntry.PATH_COLUMN
            };

            List<Track> tracks = new ArrayList<>();
            Cursor c = helper.getReadableDatabase().query(MusicEntry.TABLE, projection, null, null, null, null, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                tracks.add(
                        new Track(
                                c.getString(c.getColumnIndex(MusicEntry.TITLE_COLUMN)),
                                c.getString(c.getColumnIndex(MusicEntry.ARTIST_COLUMN)),
                                c.getString(c.getColumnIndex(MusicEntry.PATH_COLUMN))
                        )
                );
                c.moveToNext();
            }
            subscriber.onNext(tracks);
            c.close();
        });
    }

    public void putFromStorage() {
        File musicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        File[] files = musicDir.listFiles();
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        for (File f : files) {
            if (f.isDirectory()) continue;
            retriever.setDataSource(f.getAbsolutePath());
            ContentValues v = new ContentValues();
            v.put(MusicEntry.PATH_COLUMN, f.getAbsolutePath());
            v.put(MusicEntry.TITLE_COLUMN,
                    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
            v.put(MusicEntry.ARTIST_COLUMN,
                    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
            helper.getWritableDatabase().insert(MusicEntry.TABLE, null, v);
        }
        retriever.release();
    }

}
