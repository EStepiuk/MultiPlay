package ua.stepiukyevhen.multiplay.view.fragment;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ua.stepiukyevhen.multiplay.MultiPlayApp;
import ua.stepiukyevhen.multiplay.R;
import ua.stepiukyevhen.multiplay.data.DAO;
import ua.stepiukyevhen.multiplay.databinding.SwipeRefreshListBinding;
import ua.stepiukyevhen.multiplay.di.DaggerStorageFragmentComponent;
import ua.stepiukyevhen.multiplay.di.StorageFragmentComponent;
import ua.stepiukyevhen.multiplay.inteface.HasComponent;
import ua.stepiukyevhen.multiplay.inteface.PlayerBus;
import ua.stepiukyevhen.multiplay.view.adapter.TrackListAdapter;

public class StorageFragment extends Fragment implements HasComponent<StorageFragmentComponent> {

    private static final String STORAGE = "storage";

    private SwipeRefreshListBinding binding;
    private StorageFragmentComponent component;
    private TrackListAdapter adapter;

    private String playListTag = STORAGE;

    @Inject DAO dao;

    private PlayerBus playerBus;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        playerBus = (PlayerBus) context;
    }

    @Override
    public void onDetach() {
        playerBus = null;

        super.onDetach();
    }

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

        setupListView();
        binding.swipeRefreshLayout.setOnRefreshListener(this::onRefresh);
        binding.swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    private void setupComponent() {
        component = DaggerStorageFragmentComponent
                .builder()
                .appComponent(MultiPlayApp.get(getActivity()).getComponent())
                .build();

        component.inject(this);
    }

    private void setupListView() {
        adapter = new TrackListAdapter(R.layout.list_item);
        adapter.setOnItemClickListener(position -> {
            if (!playListTag.equals(playerBus.player().getCurrentPlaylistTag())) {
                //TODO: reimplement
                playerBus.player().setPlayList(playListTag, new Gson().toJson(adapter.getItems()));
            }

            playerBus.player().playAtIndex(position);
        });
        binding.list.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.list.setAdapter(adapter);
    }

    private void onRefresh() {
        dao.getTracks()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        items -> {
                            adapter.replaceItems(items);
                            binding.swipeRefreshLayout.setRefreshing(false);
                        },
                        Throwable::printStackTrace
                );
    }

    @Override
    public StorageFragmentComponent getComponent() {
        return component;
    }
}
