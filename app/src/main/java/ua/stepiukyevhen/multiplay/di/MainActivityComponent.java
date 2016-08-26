package ua.stepiukyevhen.multiplay.di;;

import dagger.Component;
import ua.stepiukyevhen.multiplay.di.scopes.ActivityScope;
import ua.stepiukyevhen.multiplay.view.activity.MainActivity;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
