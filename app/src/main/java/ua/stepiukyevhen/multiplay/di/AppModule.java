package ua.stepiukyevhen.multiplay.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.plugins.RxJavaPlugins;
import ua.stepiukyevhen.multiplay.data.DAO;
import ua.stepiukyevhen.multiplay.data.MusicDbHelper;
import ua.stepiukyevhen.multiplay.di.scopes.AppScope;
import ua.stepiukyevhen.multiplay.intefaces.SoundCloudAPI;


@Module
public class AppModule {

    static final String DEFAULT_PREFS = "ua.stepiukyevhen.multiplay.prefs";

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
    public Context provideApplicationContext() {
        return app;
    }

    @Provides
    @AppScope
    public SharedPreferences provideSharedPreferences() {
        return app.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE);
    }

    @Provides
    @AppScope
    public MusicDbHelper provideHelper(Context context){
        return new MusicDbHelper(context);
    }

    @Provides
    @AppScope
    public DAO provideDao(MusicDbHelper helper) {
        return new DAO(helper);
    }

    @Provides
    @AppScope
    public SoundCloudAPI provideApi() {
        return new Retrofit.Builder()
                .baseUrl("https://api.soundcloud.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(SoundCloudAPI.class);
    }
}
