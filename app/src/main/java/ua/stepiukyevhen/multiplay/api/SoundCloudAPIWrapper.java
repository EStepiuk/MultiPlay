package ua.stepiukyevhen.multiplay.api;


import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ua.stepiukyevhen.multiplay.api.response.SoundCloudToken;
import ua.stepiukyevhen.multiplay.model.SoundCloudTrack;

public class SoundCloudAPIWrapper {

    public interface OnReceiveTokenListener {
        void onReceive(SoundCloudToken token);
        void onError(Throwable t);
    }

    private SoundCloudAPI api;
    private String token;

    private String clientId;

    private String clientSecret;
    private OnReceiveTokenListener onReceiveTokenListener;

    public SoundCloudAPIWrapper(SoundCloudAPI api, String clientId, String clientSecret) {
        this.api = api;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public void initialize(String refreshToken) {
        setupTokenObservable(
                api.getToken(clientId, clientSecret, "refresh_token", refreshToken)
        );
    }

    public void initialize(String username, String password) {
        setupTokenObservable(
                api.getToken(clientId, clientSecret, "password", username, password)
        );
    }

    public void setOnReceiveTokenListener(OnReceiveTokenListener listener) {
        this.onReceiveTokenListener = (listener);
    }

    public Observable<List<SoundCloudTrack>> getFavorites(int limit) {
        return api.getFavorites(token, limit);
    }

    public String getToken() {
        return token;
    }

    private void setupTokenObservable(Observable<SoundCloudToken> observable) {
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(
                token -> {
                    Log.d("TAG", "token = " + token.getAccessToken());
                    this.token = token.getAccessToken();
                    if (onReceiveTokenListener != null)
                        onReceiveTokenListener.onReceive(token);
                },
                throwable -> {
                    Log.d("TAG", "initialize error = " + throwable.getMessage());
                    if (onReceiveTokenListener != null)
                        onReceiveTokenListener.onError(throwable);
                }
        );
    }
}
