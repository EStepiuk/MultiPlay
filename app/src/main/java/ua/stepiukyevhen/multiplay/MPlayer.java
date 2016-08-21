package ua.stepiukyevhen.multiplay;

import android.media.MediaPlayer;

import java.io.IOException;

import rx.schedulers.Schedulers;
import ua.stepiukyevhen.multiplay.model.base.Track;


public class MPlayer {

    private static MediaPlayer player = new MediaPlayer();

    static {
        player.setOnPreparedListener(MediaPlayer::start);
    }

    public static void play(Track track) {
        try {
            player.reset();
            player.setDataSource(track.getDataSource());
            Schedulers.newThread()
                    .createWorker()
                    .schedule(MPlayer::prepareForPlaying);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void prepareForPlaying() {
        try {
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

