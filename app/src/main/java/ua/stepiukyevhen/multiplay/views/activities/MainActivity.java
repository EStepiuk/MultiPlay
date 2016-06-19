package ua.stepiukyevhen.multiplay.views.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import ua.stepiukyevhen.multiplay.MultiPlayApp;
import ua.stepiukyevhen.multiplay.R;
import ua.stepiukyevhen.multiplay.data.DAO;
import ua.stepiukyevhen.multiplay.databinding.ActivityMainBinding;
import ua.stepiukyevhen.multiplay.di.DaggerMainActivityComponent;
import ua.stepiukyevhen.multiplay.di.MainActivityComponent;
import ua.stepiukyevhen.multiplay.intefaces.HasComponent;
import ua.stepiukyevhen.multiplay.intefaces.SoundCloudAPI;
import ua.stepiukyevhen.multiplay.models.SoundCloudToken;
import ua.stepiukyevhen.multiplay.views.adapters.TrackListAdapter;
import ua.stepiukyevhen.multiplay.views.fragments.StorageFragment;

public class MainActivity extends AppCompatActivity implements HasComponent<MainActivityComponent> {

    private MainActivityComponent component;
    private ActivityMainBinding binding;

    @Override
    public MainActivityComponent getComponent() {
        return component;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupComponent();
        
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setupComponent() {
        component = DaggerMainActivityComponent
                .builder()
                .appComponent(MultiPlayApp.get(this).getComponent())
                .build();

        component.inject(this);
    }

    private void setupViewPager() {
        binding.container.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            Fragment[] fragments = {new StorageFragment(), new StorageFragment()};

            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        });

        binding.tabs.setupWithViewPager(binding.container);
        for (int i = 0; i < binding.tabs.getTabCount(); i++) {
            TabLayout.Tab tab = binding.tabs.getTabAt(i);
            if (tab != null)
                tab.setIcon(R.drawable.ic_audiotrack_white_24dp);
        }
    }
}
