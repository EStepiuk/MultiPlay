package ua.stepiukyevhen.multiplay.di;


import android.content.SharedPreferences;

import dagger.Component;
import ua.stepiukyevhen.multiplay.MultiPlayApp;
import ua.stepiukyevhen.multiplay.data.DAO;
import ua.stepiukyevhen.multiplay.di.scopes.AppScope;
import ua.stepiukyevhen.multiplay.intefaces.SoundCloudAPI;

@AppScope
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MultiPlayApp app);

    SoundCloudAPI api();
    DAO dao();
    SharedPreferences prefs();
}
