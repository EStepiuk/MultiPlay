package ua.stepiukyevhen.multiplay;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ua.stepiukyevhen.multiplay.di.AppComponent;
import ua.stepiukyevhen.multiplay.di.AppModule;
import ua.stepiukyevhen.multiplay.di.DaggerAppComponent;
import ua.stepiukyevhen.multiplay.intefaces.HasComponent;
import ua.stepiukyevhen.multiplay.intefaces.SoundCloudAPI;


public class MultiPlayApp extends Application implements HasComponent<AppComponent> {

    private AppComponent component;

    @Inject SharedPreferences prefs;

    @Override
    public AppComponent getComponent() {
        return component;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public static MultiPlayApp get(Context context) {
        return (MultiPlayApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setupComponent();
    }

    private void setupComponent() {
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        component.inject(this);
    }
}
