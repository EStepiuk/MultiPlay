package ua.stepiukyevhen.multiplay.model;


import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

import ua.stepiukyevhen.multiplay.model.base.Track;

public class SoundCloudTrack implements Track {

    @SerializedName("title")
    private String title;
    @SerializedName("user")
    private User user;
    @SerializedName("stream_url")
    private String stream;

    private String oauthToken;

    private static class User {
        @SerializedName("username")
        private String username;

    }

    @Override
    public String getArtist() {
        return user.username;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAlbum() {
        return null;
    }

    @Override
    public int getAlbumPosition() {
        return -1;
    }

    @Override
    public int getDuration() {
        return -1;
    }

    @Override
    public String getDataSource() {
        return stream + "?oauth_token=" + oauthToken;
    }

    @Override
    public void renderImage(ImageView view) {
        //TODO: render image with Picasso
    }

    @Override
    public Type getType() {
        return Type.SOUNDCLOUD;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }
}
