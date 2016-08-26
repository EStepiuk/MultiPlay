package ua.stepiukyevhen.multiplay.inteface.aidl;

interface PlayerServiceInterface {

    void setPlayList(String jsonPlayList, String tag);

    void play();

    void playAtIndex(int index);

    void stop();

    void pause();

    void next();

    void previous();

    void rewind(int millis);

    void setNextTrack(String jsonTrack);

    boolean isPlaying();

    String getCurrentPlaylistTag();

}
