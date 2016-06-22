package ua.stepiukyevhen.multiplay.intefaces;

import java.util.List;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import ua.stepiukyevhen.multiplay.models.SoundCloudToken;
import ua.stepiukyevhen.multiplay.models.Track;


public interface SoundCloudAPI {
    @FormUrlEncoded
    @POST("oauth2/token")
    Observable<SoundCloudToken> getToken(@Field("client_id") String clientId,
                                         @Field("client_secret") String clientSecret,
                                         @Field("grant_type") String grantType,
                                         @Field("username") String username,
                                         @Field("password") String password);

    @FormUrlEncoded
    @POST("oauth2/token")
    Observable<SoundCloudToken> getToken(@Field("client_id") String clientId,
                                         @Field("client_secret") String clientSecret,
                                         @Field("grant_type") String grantType,
                                         @Field("refresh_token") String refreshToken);

    @GET("me/favorites")
    Observable<List<Track>> getFavorites(@Query("oauth_token") String token,
                                         @Query("limit") int limit);
}
