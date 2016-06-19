package ua.stepiukyevhen.multiplay.models;


import android.graphics.Bitmap;

public class Track {

    private String title;
    private String author;
    private String filepath;

    public Track(String title, String author, String filepath) {
        this.title = title;
        this.author = author;
        this.filepath = filepath;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getAuthor() {
        return author;
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
