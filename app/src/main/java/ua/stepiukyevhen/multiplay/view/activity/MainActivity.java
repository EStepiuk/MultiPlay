package ua.stepiukyevhen.multiplay.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import ua.stepiukyevhen.multiplay.MultiPlayApp;
import ua.stepiukyevhen.multiplay.R;
import ua.stepiukyevhen.multiplay.databinding.ActivityMainBinding;
import ua.stepiukyevhen.multiplay.di.DaggerMainActivityComponent;
import ua.stepiukyevhen.multiplay.di.MainActivityComponent;
import ua.stepiukyevhen.multiplay.inteface.FragmentContainer;
import ua.stepiukyevhen.multiplay.inteface.HasComponent;
import ua.stepiukyevhen.multiplay.util.MultiPlayPreferences;
import ua.stepiukyevhen.multiplay.view.fragment.LoginFragment;
import ua.stepiukyevhen.multiplay.view.fragment.SoundCloudFragment;
import ua.stepiukyevhen.multiplay.view.fragment.StorageFragment;

public class MainActivity extends AppCompatActivity implements HasComponent<MainActivityComponent>,
        NavigationView.OnNavigationItemSelectedListener, FragmentContainer {

    private MainActivityComponent component;
    private ActivityMainBinding binding;

    @Inject MultiPlayPreferences prefs;

    @Override
    public MainActivityComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setupComponent();
        
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(binding.mainScreen.toolbar);
        setupNavigationView();

        if (savedInstanceState == null) {
            replaceFragment(new StorageFragment());
        }
    }

    private void setupNavigationView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.mainScreen.toolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navigationView.setCheckedItem(R.id.nav_storage);
        binding.navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_storage:
                replaceFragment(new StorageFragment());
                break;

            case R.id.nav_soundcloud:
                if (prefs.getSavedUsername() == null) {
                    replaceFragment(new LoginFragment());
                } else {
                    replaceFragment(new SoundCloudFragment());
                }
                break;
        }

        binding.drawerLayout.closeDrawers();
        return true;
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

    @Override
    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.mainScreen.container.getId(), fragment)
                .commit();
    }
}
