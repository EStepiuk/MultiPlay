package ua.stepiukyevhen.multiplay;

import android.app.Application;
import android.content.Context;

import ua.stepiukyevhen.multiplay.di.AppComponent;
import ua.stepiukyevhen.multiplay.di.AppModule;
import ua.stepiukyevhen.multiplay.di.DaggerAppComponent;
import ua.stepiukyevhen.multiplay.inteface.HasComponent;


public class MultiPlayApp extends Application implements HasComponent<AppComponent> {

    private AppComponent component;

    @Override
    public AppComponent getComponent() {
        return component;
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
