package ua.stepiukyevhen.multiplay;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;

import java.io.IOException;

import rx.schedulers.Schedulers;
import ua.stepiukyevhen.multiplay.models.Track;
import ua.stepiukyevhen.multiplay.views.activities.MainActivity;
import ua.stepiukyevhen.multiplay.views.fragments.SoundCloudFragment;


public class MPlayer {

    private static MediaPlayer player = new MediaPlayer();

    static {
        player.setOnPreparedListener(MediaPlayer::start);
    }

    public static View.OnClickListener play(Track track, Context context) {
        return v -> {
            String stream = track.getFilepath(
                    MultiPlayApp.get(context)
                            .getPrefs()
                            .getString(SoundCloudFragment.TOKEN_KEY, null));
            try {
                player.reset();
                player.setDataSource(stream);
                Schedulers.newThread()
                        .createWorker()
                        .schedule(MPlayer::prepareForPlaying);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private static void prepareForPlaying() {
        try {
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

