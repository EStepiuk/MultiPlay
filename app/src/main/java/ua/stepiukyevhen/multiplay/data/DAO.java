package ua.stepiukyevhen.multiplay.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import ua.stepiukyevhen.multiplay.model.StorageTrack;

import static ua.stepiukyevhen.multiplay.data.Contract.MusicEntry;


public class DAO {

    private static final String[] PROJECTION = {
            MusicEntry.ARTIST_COLUMN,
            MusicEntry.TITLE_COLUMN,
            MusicEntry.PATH_COLUMN,
            MusicEntry.ALBUM_COLUMN,
            MusicEntry.LENGTH_COLUMN,
    };

    private final MusicDbHelper helper;

    public DAO(MusicDbHelper helper) {
        this.helper = helper;
    }

    public Observable<List<StorageTrack>> getTracks() {
        return Observable.create(subscriber -> {
            Cursor c = helper
                    .getReadableDatabase()
                    .query(MusicEntry.TABLE, PROJECTION, null, null, null, null, null);

            List<StorageTrack> tracks = new ArrayList<>();
            while (c.moveToNext()) {
                tracks.add(
                        new StorageTrack.Builder()
                                .setTitle(c.getString(c.getColumnIndex(MusicEntry.TITLE_COLUMN)))
                                .setArtist(c.getString(c.getColumnIndex(MusicEntry.ARTIST_COLUMN)))
                                .setDataSource(c.getString(c.getColumnIndex(MusicEntry.PATH_COLUMN)))
                                .setAlbum(c.getString(c.getColumnIndex(MusicEntry.ALBUM_COLUMN)))
//                                .setArtSource(c.getBlob(c.getColumnIndex(MusicEntry.IMAGE_COLUMN)))
                                .setDuration(c.getInt(c.getColumnIndex(MusicEntry.LENGTH_COLUMN)))
                                .build()
                );

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
            v.put(MusicEntry.ALBUM_COLUMN,
                    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
            v.put(MusicEntry.LENGTH_COLUMN,
                    retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            helper.getWritableDatabase().insert(MusicEntry.TABLE, null, v);
        }
        retriever.release();
    }

}
