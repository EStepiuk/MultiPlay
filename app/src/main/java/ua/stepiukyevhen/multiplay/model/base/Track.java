package ua.stepiukyevhen.multiplay.model.base;


import android.widget.ImageView;

public interface Track {

    String getArtist();

    String getTitle();

    String getAlbum();

    String getDataSource();

    int getAlbumPosition();

    int getDuration();

    void renderImage(ImageView view);

    Type getType();

    enum Type {
        STORAGE,
        SOUNDCLOUD,
        SOUNDCLOUD_CACHE
    }

}
