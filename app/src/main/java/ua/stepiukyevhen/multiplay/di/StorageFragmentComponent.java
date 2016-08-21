package ua.stepiukyevhen.multiplay.di;


import dagger.Component;
import ua.stepiukyevhen.multiplay.di.scopes.FragmentScope;
import ua.stepiukyevhen.multiplay.view.fragment.StorageFragment;


@FragmentScope
@Component(dependencies = AppComponent.class, modules = StorageFragmentModule.class)
public interface StorageFragmentComponent {
    void inject(StorageFragment fragment);
}
