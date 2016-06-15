package ua.stepiukyevhen.multiplay.views.activities;

import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import ua.stepiukyevhen.multiplay.MultiPlayApp;
import ua.stepiukyevhen.multiplay.R;
import ua.stepiukyevhen.multiplay.data.DAO;
import ua.stepiukyevhen.multiplay.data.MusicDbHelper;
import ua.stepiukyevhen.multiplay.databinding.ActivityMainBinding;
import ua.stepiukyevhen.multiplay.di.DaggerMainActivityComponent;
import ua.stepiukyevhen.multiplay.di.MainActivityComponent;
import ua.stepiukyevhen.multiplay.intefaces.HasComponent;
import ua.stepiukyevhen.multiplay.intefaces.SoundCloudAPI;
import ua.stepiukyevhen.multiplay.models.SoundCloudToken;
import ua.stepiukyevhen.multiplay.models.Track;
import ua.stepiukyevhen.multiplay.views.adapters.TrackListAdapter;

public class MainActivity extends AppCompatActivity implements HasComponent<MainActivityComponent> {

    private MainActivityComponent component;
    private ActivityMainBinding binding;
    private TrackListAdapter adapter;

    @Override
    public MainActivityComponent getComponent() {
        return component;
    }

    private SoundCloudToken token;
    @Inject SoundCloudAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupComponent();
        
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(binding.toolbar);
        setupRecyclerView();

        MusicDbHelper helper = new MusicDbHelper(this);
        helper
                .getReadableDb()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(db -> adapter.addItems(DAO.getTracks(db)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private Observable<List<Track>> getStorageMusic() {
        return Observable.create(subscriber -> {
                    File musicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
                    File[] files = musicDir.listFiles();
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    int counter = 0;
                    List<Track> tracks = new ArrayList<>();
                    for (File f : files) {
                        if (counter++ == 9) {
                            subscriber.onNext(tracks);
                            tracks.clear();
                        }
                        if (f.isDirectory()) continue;
                        retriever.setDataSource(f.getAbsolutePath());
                        tracks.add(
                                new Track(
                                        retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                                        retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST))
                        );
                    }
                    subscriber.onNext(tracks);
                    subscriber.onCompleted();
                    retriever.release();
                });
    }

    private void setupRecyclerView() {
        adapter = new TrackListAdapter();
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(adapter);
    }

    private void setupComponent() {
        component = DaggerMainActivityComponent
                .builder()
                .appComponent(MultiPlayApp.get(this).getComponent())
                .build();

        component.inject(this);
    }
}
