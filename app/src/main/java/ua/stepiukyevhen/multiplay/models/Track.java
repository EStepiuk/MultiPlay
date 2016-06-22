package ua.stepiukyevhen.multiplay.models;


import com.google.gson.annotations.SerializedName;

public class Track {

    class User {
        @SerializedName("username")
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    @SerializedName("title")
    private String title;
    @SerializedName("user")
    private User user;
    @SerializedName("stream_url")
    private String stream;
    private String filepath;
    private String author;

    public Track(String title, String author, String filepath) {
        this.title = title;
        this.author = author;
        this.filepath = filepath;
    }

    public String getFilepath(String token) {
        return (stream != null) ? stream + "?oauth_token=" + token: filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getAuthor() {
        return (user != null) ? user.username : author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
