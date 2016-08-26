package ua.stepiukyevhen.multiplay.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import ua.stepiukyevhen.multiplay.inteface.aidl.PlayerServiceInterface;
import ua.stepiukyevhen.multiplay.model.base.Track;

import static java.util.Arrays.asList;


public class PlayerService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener {

    private MediaPlayer player = new MediaPlayer();
    private ArrayList<Track> queue = new ArrayList<>();
    private ArrayList<Track> playList;
    private String playListTag;
    private Track nextTrack;
    private int playListPosition;
    private int queuePosition = -1;

    private final Gson gson = new Gson();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return playerBus;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (playListPosition != playList.size() - 1 || (!queue.isEmpty() && queuePosition == -1)) next();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    private void setPlayList(String playlist, String tag) {
        clearQueue();
        playList = new ArrayList<>(asList(new Gson().fromJson(playlist, Track[].class)));
        playListTag = tag;
    }

    //TODO: implement next track logic (with current track logic)
    private void play() {
        Track track = getNextTrack();

        if (track == null) return;
        try {
            player.setDataSource(track.getDataSource());
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playAtIndex(int index) {
        if (index < playList.size()) {
            clearQueue();
            playListPosition = index;
            play();
        }
    }

    private void stop() {
        player.stop();
    }

    private void pause() {
        player.pause();
    }

    private void next() {
        if (!queue.isEmpty()) {
            queuePosition++;
        } else {
            playListPosition++;
        }
        play();
    }

    private void previous() {
        if (!queue.isEmpty() && queuePosition != -1) {
            queuePosition--;
        } else {
            playListPosition--;
        }
        play();
    }

    private void rewind(int millis) {
        if (millis > 0 && millis < player.getDuration()) {
            player.seekTo(millis);
        }
    }

    private void clearQueue() {
        queue.clear();
        queuePosition = -1;
    }

    private Track getNextTrack() {
        if (nextTrack != null) return nextTrack;
        if (queuePosition >= 0 && queuePosition < queue.size()) return queue.get(queuePosition);
        if (playList != null && playList.isEmpty()) {
            return playList.get(
                    playListPosition < 0 ? playList.size() - 1 :
                            playListPosition >= playList.size() ? 0 :
                                    playListPosition
            );
        }
        return null;
    }

    private PlayerServiceInterface.Stub playerBus = new PlayerServiceInterface.Stub() {
        @Override
        public void setPlayList(String playlist, String tag) throws RemoteException {
            PlayerService.this.setPlayList(playlist, tag);
        }

        @Override
        public void play() throws RemoteException {
            PlayerService.this.play();
        }

        @Override
        public void playAtIndex(int index) throws RemoteException {
            PlayerService.this.playAtIndex(index);
        }

        @Override
        public void stop() throws RemoteException {
            PlayerService.this.stop();
        }

        @Override
        public void pause() throws RemoteException {
            PlayerService.this.pause();
        }

        @Override
        public void next() throws RemoteException {
            PlayerService.this.next();
        }

        @Override
        public void previous() throws RemoteException {
            PlayerService.this.previous();
        }

        @Override
        public void rewind(int millis) throws RemoteException {
            PlayerService.this.rewind(millis);
        }

        @Override
        public void setNextTrack(String jsonTrack) throws RemoteException {
            nextTrack = gson.fromJson(jsonTrack, Track.class);
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            return player.isPlaying();
        }

        @Override
        public String getCurrentPlaylistTag() throws RemoteException {
            return playListTag;
        }
    };
}
