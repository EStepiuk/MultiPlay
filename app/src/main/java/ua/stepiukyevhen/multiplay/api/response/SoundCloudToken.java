package ua.stepiukyevhen.multiplay.api.response;


public class SoundCloudToken {

    private String access_token;
    private String scope;
    private String refresh_token;

    public String getRefreshToken() {
        return refresh_token;
    }

    public String getAccessToken() {
        return access_token;
    }
}
