package ua.stepiukyevhen.multiplay.di;


import dagger.Component;
import ua.stepiukyevhen.multiplay.MultiPlayApp;
import ua.stepiukyevhen.multiplay.api.SoundCloudAPIWrapper;
import ua.stepiukyevhen.multiplay.data.DAO;
import ua.stepiukyevhen.multiplay.di.scopes.AppScope;
import ua.stepiukyevhen.multiplay.util.MultiPlayPreferences;

@AppScope
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MultiPlayApp app);

    SoundCloudAPIWrapper api();
    MultiPlayPreferences prefs();
    DAO dao();
}
