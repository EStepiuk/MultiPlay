package ua.stepiukyevhen.multiplay;

import android.media.MediaPlayer;
import android.view.View;

import java.io.IOException;


public class MPlayer {

    private static MediaPlayer player = new MediaPlayer();

    static {
        player.setOnPreparedListener(MediaPlayer::start);
    }

    public static View.OnClickListener play(String path) {
        return v -> {
            try {
                player.reset();
                player.setDataSource(path);
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}

