package ua.stepiukyevhen.multiplay.di;;

import dagger.Component;
import ua.stepiukyevhen.multiplay.di.scopes.LoginScope;
import ua.stepiukyevhen.multiplay.view.activity.MainActivity;

@LoginScope
@Component(dependencies = AppComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
