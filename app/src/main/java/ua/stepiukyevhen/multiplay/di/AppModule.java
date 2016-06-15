package ua.stepiukyevhen.multiplay.di;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.stepiukyevhen.multiplay.di.scopes.AppScope;
import ua.stepiukyevhen.multiplay.intefaces.SoundCloudAPI;


@Module
public class AppModule {

    private final Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides
    @AppScope
    public Application provideApp() {
        return app;
    }

    @Provides
    @AppScope
    public SoundCloudAPI provideApi() {
        return new Retrofit.Builder()
                .baseUrl("https://api.soundcloud.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SoundCloudAPI.class);
    }
}
