package ua.stepiukyevhen.multiplay.di;


import dagger.Component;
import ua.stepiukyevhen.multiplay.di.scopes.FragmentScope;
import ua.stepiukyevhen.multiplay.view.activity.SplashActivity;

@FragmentScope
@Component(dependencies = AppComponent.class)
public interface SplashActivityComponent {
    void inject(SplashActivity splashActivity);
}