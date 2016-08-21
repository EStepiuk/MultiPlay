package ua.stepiukyevhen.multiplay.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import rx.schedulers.Schedulers;
import ua.stepiukyevhen.multiplay.MultiPlayApp;
import ua.stepiukyevhen.multiplay.R;
import ua.stepiukyevhen.multiplay.api.SoundCloudAPIWrapper;
import ua.stepiukyevhen.multiplay.data.DAO;
import ua.stepiukyevhen.multiplay.di.DaggerSplashActivityComponent;
import ua.stepiukyevhen.multiplay.di.SplashActivityComponent;
import ua.stepiukyevhen.multiplay.inteface.HasComponent;
import ua.stepiukyevhen.multiplay.util.MultiPlayPreferences;


public class SplashActivity extends AppCompatActivity implements HasComponent<SplashActivityComponent> {

    private SplashActivityComponent component;

    @Inject DAO dao;
    @Inject MultiPlayPreferences prefs;
    @Inject SoundCloudAPIWrapper api;

    @Override
    public SplashActivityComponent getComponent() {
        return component;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setupComponent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        if (prefs.isFirstLaunch()) {
            Schedulers.newThread()
                    .createWorker()
                    .schedule(() -> {
                        dao.putFromStorage();
                        prefs.setIsFirstLaunch(false);

                        startActivity(new Intent(this, MainActivity.class));
                    });
        } else {
            //TODO: initialize SoundCloudAPIWrapper
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void setupComponent() {
        component = DaggerSplashActivityComponent
                .builder()
                .appComponent(MultiPlayApp.get(this).getComponent())
                .build();

        component.inject(this);
    }
}
