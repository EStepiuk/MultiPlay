package ua.stepiukyevhen.multiplay.di;


import dagger.Component;
import ua.stepiukyevhen.multiplay.di.scopes.FragmentScope;
import ua.stepiukyevhen.multiplay.view.fragment.SoundCloudFragment;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = SoundCloudFragmentModule.class)
public interface SoundCloudFragmentComponent {
    void inject(SoundCloudFragment fragment);
}
