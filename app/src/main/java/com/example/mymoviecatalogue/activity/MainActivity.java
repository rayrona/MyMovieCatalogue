package com.example.mymoviecatalogue.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mymoviecatalogue.R;
import com.example.mymoviecatalogue.fragment.FavFragment;
import com.example.mymoviecatalogue.fragment.MovFragment;
import com.example.mymoviecatalogue.fragment.SearchFragment;
import com.example.mymoviecatalogue.fragment.TvFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navMenu)
    BottomNavigationView navigation;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setTitle(R.string.title_tab1);
        }
        if (savedInstanceState == null) {
            loadFragment(new MovFragment());
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            toolbar.collapseActionView();
            switch (menuItem.getItemId()) {
                case R.id.movies:
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle(R.string.title_tab1);
                    fragment = new MovFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.tvshows:
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle(R.string.title_tab2);
                    fragment = new TvFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.favorites:
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setTitle(R.string.title_tab3);
                    fragment = new FavFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };


    private void setupSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() > 0) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("query", s);
                    SearchFragment fragment = new SearchFragment();
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.frame, fragment);
                    transaction.commit();
                    navigation.getMenu().setGroupCheckable(0, false, true);
                } else {
                    navigation.getMenu().setGroupCheckable(0, true, true);
                    navigation.getMenu().getItem(0).setChecked(true);
                    loadFragment(new MovFragment());
                    if (getSupportActionBar() != null) {
                        toolbar.setTitle(R.string.title_tab1);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        setupSearchView();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.local_btn:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
            case R.id.notif_btn:
                Intent intent = new Intent(MainActivity.this, NotifSettingActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }



}
