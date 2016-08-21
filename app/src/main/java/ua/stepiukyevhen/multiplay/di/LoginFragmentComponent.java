package ua.stepiukyevhen.multiplay.di;


import dagger.Component;
import ua.stepiukyevhen.multiplay.di.scopes.FragmentScope;
import ua.stepiukyevhen.multiplay.view.fragment.LoginFragment;

@FragmentScope
@Component(dependencies = AppComponent.class)
public interface LoginFragmentComponent {
    void inject(LoginFragment fragment);
}
