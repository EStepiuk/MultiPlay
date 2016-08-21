package ua.stepiukyevhen.multiplay.model;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import ua.stepiukyevhen.multiplay.model.base.Track;

public class StorageTrack implements Track {

    private String artist;
    private String album;
    private String title;
    private String dataSource;
    private byte[] artSource;
    private int duration;
    private int albumPosition;

    private StorageTrack() {}

    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAlbum() {
        return album;
    }

    @Override
    public int getAlbumPosition() {
        return albumPosition;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public String getDataSource() {
        return dataSource;
    }

    @Override
    public void renderImage(ImageView view) {
        //TODO: render image with Picasso
    }

    @Override
    public Type getType() {
        return Type.STORAGE;
    }

    public static class Builder {

        private StorageTrack instance = new StorageTrack();

        public StorageTrack.Builder setArtist(String artist) {
            instance.artist = artist;
            return this;
        }

        public StorageTrack.Builder setTitle(String title) {
            instance.title = title;
            return this;
        }

        public StorageTrack.Builder setAlbum(String album) {
            instance.album = album;
            return this;
        }

        public StorageTrack.Builder setDataSource(String dataSource) {
            instance.dataSource = dataSource;
            return this;
        }

        public StorageTrack.Builder setArtSource(byte[] artSource) {
            instance.artSource = artSource;
            return this;
        }

        public StorageTrack.Builder setDuration(int duration) {
            instance.duration = duration;
            return this;
        }

        public StorageTrack.Builder setAlbumPosition(int albumPosition) {
            instance.albumPosition = albumPosition;
            return this;
        }

        @Nullable
        public StorageTrack build() {
            if (instance.dataSource != null || instance.duration == 0) {
                if (instance.title == null) {
                    String[] tmp = instance.dataSource.split("/");
                    String filename = tmp[tmp.length - 1];
                    instance.title = filename.substring(0, filename.length() - 4);
                }

                if (instance.album == null) {
                    instance.album = "Unknown";
                }

                if (instance.artist == null) {
                    instance.artist = "Unknown";
                }

                return instance;
            } else {
                return null;
            }
        }

    }
}
