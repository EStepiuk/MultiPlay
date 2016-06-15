package ua.stepiukyevhen.multiplay.di;


import dagger.Component;
import ua.stepiukyevhen.multiplay.MultiPlayApp;
import ua.stepiukyevhen.multiplay.di.scopes.AppScope;
import ua.stepiukyevhen.multiplay.intefaces.SoundCloudAPI;

@AppScope
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MultiPlayApp app);

    SoundCloudAPI api();
}
