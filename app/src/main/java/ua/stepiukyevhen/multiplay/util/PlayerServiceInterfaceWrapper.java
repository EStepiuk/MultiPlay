package ua.stepiukyevhen.multiplay.util;


import android.os.RemoteException;

import ua.stepiukyevhen.multiplay.inteface.aidl.PlayerServiceInterface;

public class PlayerServiceInterfaceWrapper {

    private PlayerServiceInterface.Stub playerServiceInterface;

    public PlayerServiceInterfaceWrapper(PlayerServiceInterface.Stub playerServiceInterface) {
        this.playerServiceInterface = playerServiceInterface;
    }

    public void setPlayList(String jsonPlayList, String tag) {
        try {
            playerServiceInterface.setPlayList(jsonPlayList, tag);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        try {
            playerServiceInterface.play();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void playAtIndex(int index) {
        try {
            playerServiceInterface.playAtIndex(index);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            playerServiceInterface.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        try {
            playerServiceInterface.pause();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void next() {
        try {
            playerServiceInterface.next();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void previous() {
        try {
            playerServiceInterface.previous();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void rewind(int millis) {
        try {
            playerServiceInterface.rewind(millis);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setNextTrack(String jsonTrack) {
        try {
            playerServiceInterface.setNextTrack(jsonTrack);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlaying() {
        try {
            return playerServiceInterface.isPlaying();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getCurrentPlaylistTag() {
        try {
            return playerServiceInterface.getCurrentPlaylistTag();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

}
