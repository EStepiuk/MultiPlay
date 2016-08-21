package ua.stepiukyevhen.multiplay.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ua.stepiukyevhen.multiplay.MultiPlayApp;
import ua.stepiukyevhen.multiplay.R;
import ua.stepiukyevhen.multiplay.api.SoundCloudAPIWrapper;
import ua.stepiukyevhen.multiplay.api.response.SoundCloudToken;
import ua.stepiukyevhen.multiplay.model.SoundCloudTrack;
import ua.stepiukyevhen.multiplay.data.DAO;
import ua.stepiukyevhen.multiplay.databinding.SwipeRefreshListBinding;
import ua.stepiukyevhen.multiplay.di.DaggerSoundCloudFragmentComponent;
import ua.stepiukyevhen.multiplay.di.SoundCloudFragmentComponent;
import ua.stepiukyevhen.multiplay.inteface.HasComponent;
import ua.stepiukyevhen.multiplay.util.MultiPlayPreferences;
import ua.stepiukyevhen.multiplay.view.adapter.TrackListAdapter;


public class SoundCloudFragment extends Fragment implements HasComponent<SoundCloudFragmentComponent> {

    private SwipeRefreshListBinding binding;
    private SoundCloudFragmentComponent component;
    private TrackListAdapter adapter;

    @Inject SoundCloudAPIWrapper api;
    @Inject MultiPlayPreferences prefs;
    @Inject DAO dao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setupComponent();

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.swipe_refresh_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO: remove api initialization from fragment
//        setupApi();
        setupListView();
        binding.swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        binding.swipeRefreshLayout.setRefreshing(true);
        onRefresh();
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

    private void setupApi() {
        api.setOnReceiveTokenListener(new SoundCloudAPIWrapper.OnReceiveTokenListener() {
            @Override
            public void onReceive(SoundCloudToken token) {
                prefs.setRefreshToken(token.getRefreshToken());
                binding.swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }

            @Override
            public void onError(Throwable t) {

            }
        });
        api.initialize(prefs.getRefreshToken());
    }

    private void setupListView() {
        adapter = new TrackListAdapter(R.layout.list_item);
        binding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.list.setAdapter(adapter);
    }

    private void onRefresh() {
        Log.d("TAG", "onRefresh()");
        api.getFavorites(200)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        items -> {
                            Log.d("TAG", "onRefresh() completed");
                            for (SoundCloudTrack item : items) {
                                item.setOauthToken(api.getToken());
                            }
                            adapter.replaceItems(items);
                            binding.swipeRefreshLayout.setRefreshing(false);
                        },
                        throwable -> Log.d("TAG", "refresh error = " + throwable.getMessage())
                );

    }
}
