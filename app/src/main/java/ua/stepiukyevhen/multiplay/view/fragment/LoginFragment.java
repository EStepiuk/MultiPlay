package ua.stepiukyevhen.multiplay.view.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ua.stepiukyevhen.multiplay.MultiPlayApp;
import ua.stepiukyevhen.multiplay.R;
import ua.stepiukyevhen.multiplay.api.SoundCloudAPIWrapper;
import ua.stepiukyevhen.multiplay.api.response.SoundCloudToken;
import ua.stepiukyevhen.multiplay.databinding.LoginFragmentBinding;
import ua.stepiukyevhen.multiplay.di.DaggerLoginFragmentComponent;
import ua.stepiukyevhen.multiplay.di.LoginFragmentComponent;
import ua.stepiukyevhen.multiplay.inteface.FragmentContainer;
import ua.stepiukyevhen.multiplay.inteface.HasComponent;
import ua.stepiukyevhen.multiplay.util.MultiPlayPreferences;


public class LoginFragment extends Fragment implements HasComponent<LoginFragmentComponent> {

    private LoginFragmentComponent component;
    private LoginFragmentBinding binding;

    private FragmentContainer fragmentContainer;

    @Inject SoundCloudAPIWrapper api;
    @Inject MultiPlayPreferences prefs;

    @Override
    public LoginFragmentComponent getComponent() {
        return component;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        fragmentContainer = (FragmentContainer) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setupComponent();

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupApi();
        setupButton();
    }

    private void setupComponent() {
        component = DaggerLoginFragmentComponent
                .builder()
                .appComponent(MultiPlayApp.get(getActivity()).getComponent())
                .build();

        component.inject(this);
    }

    private void setupApi() {
        api.setOnReceiveTokenListener(new SoundCloudAPIWrapper.OnReceiveTokenListener() {
            @Override
            public void onReceive(SoundCloudToken token) {
                prefs.setRefreshToken(token.getRefreshToken());
                prefs.setSavedUsername(binding.loginEditText.getText().toString());
                prefs.setSavedPassword(binding.passwordEditText.getText().toString());
                fragmentContainer.replaceFragment(new SoundCloudFragment());
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    private void setupButton() {
        binding.nextButton.setOnClickListener(view -> {
            if (binding.loginEditText.getText().length() != 0
                    && binding.passwordEditText.getText().length() != 0) {

                api.initialize(
                        binding.loginEditText.getText().toString(),
                        binding.passwordEditText.getText().toString());
            }
        });
    }
}
