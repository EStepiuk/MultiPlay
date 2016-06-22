package ua.stepiukyevhen.multiplay.views.fragments;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ua.stepiukyevhen.multiplay.MultiPlayApp;
import ua.stepiukyevhen.multiplay.R;
import ua.stepiukyevhen.multiplay.data.DAO;
import ua.stepiukyevhen.multiplay.databinding.SwipeRefreshListBinding;
import ua.stepiukyevhen.multiplay.di.DaggerSoundCloudFragmentComponent;
import ua.stepiukyevhen.multiplay.di.SoundCloudFragmentComponent;
import ua.stepiukyevhen.multiplay.intefaces.HasComponent;
import ua.stepiukyevhen.multiplay.intefaces.SoundCloudAPI;
import ua.stepiukyevhen.multiplay.models.SoundCloudToken;
import ua.stepiukyevhen.multiplay.views.adapters.TrackListAdapter;


public class SoundCloudFragment extends Fragment implements HasComponent<SoundCloudFragmentComponent> {

    static final String CLIENT_ID = "2e741f3f97aa6ddff63f39794dbcde55";
    static final String CLIENT_SECRET = "c240786931e20ea4803fe8e74097fb01";
    static final String GRANT_TYPE_PASSWORD = "password";
    static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    static final String USERNAME = "dnt69";
    static final String PASSWORD = "stepyuk";
    public static final String TOKEN_KEY = "token";
    static final String REFRESH_TOKEN_KEY = GRANT_TYPE_REFRESH_TOKEN;

    private SwipeRefreshListBinding binding;
    private SoundCloudFragmentComponent component;
    private TrackListAdapter adapter;

    @Inject SoundCloudAPI api;
    @Inject SharedPreferences prefs;
    @Inject DAO dao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.swipe_refresh_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupComponent();
        setupListView();
        binding.swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        initToken();
    }

    @Override
    public SoundCloudFragmentComponent getComponent() {
        return component;
    }

    private void setupComponent() {
        component = DaggerSoundCloudFragmentComponent
                .builder()
                .appComponent(MultiPlayApp.get(getActivity()).getComponent())
                .build();

        component.inject(this);
    }

    private void setupListView() {
        adapter = new TrackListAdapter();
        binding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.list.setAdapter(adapter);
    }



    private void initToken() {
        String refreshToken = prefs.getString(REFRESH_TOKEN_KEY, null);
        Observable<SoundCloudToken> tokenRequest;
        if (refreshToken == null)
            tokenRequest = api.getToken(
                    CLIENT_ID,
                    CLIENT_SECRET,
                    GRANT_TYPE_PASSWORD,
                    USERNAME,
                    PASSWORD
            );
        else
            tokenRequest = api.getToken(
                    CLIENT_ID,
                    CLIENT_SECRET,
                    GRANT_TYPE_REFRESH_TOKEN,
                    refreshToken
            );

        tokenRequest
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(token -> {
                    prefs.edit()
                            .putString(TOKEN_KEY, token.getAccessToken())
                            .putString(REFRESH_TOKEN_KEY, token.getRefreshToken())
                            .commit();

                    Log.d("TAG", "token: " + token.getAccessToken());
                    onRefresh();
                }, Throwable::printStackTrace);
    }

    private void onRefresh() {
        api.getFavorites(prefs.getString(SoundCloudFragment.TOKEN_KEY, null), 200)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> {
                    adapter.replaceItems(items);
                    Log.v("TAG", adapter.getItemCount() + "");
                });

        binding.swipeRefreshLayout.setRefreshing(false);
    }
}
