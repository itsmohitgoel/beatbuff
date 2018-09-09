package com.itsmohitgoel.beatbuff;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.itsmohitgoel.beatbuff.fragments.AlbumFragment;
import com.itsmohitgoel.beatbuff.fragments.AllSongsFragment;
import com.itsmohitgoel.beatbuff.fragments.ArtistFragment;
import com.itsmohitgoel.beatbuff.fragments.GenreFragment;
import com.itsmohitgoel.beatbuff.fragments.HomeFragment;
import com.itsmohitgoel.beatbuff.fragments.PlaylistFragment;

public class HomeActivity extends SingleFragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    public Fragment createFragment() {
        return HomeFragment.newInstance();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set a toolbar to replace the Actionbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.home_fragment:
                fragment = HomeFragment.newInstance();
                break;
            case R.id.genre_fragment:
                fragment = GenreFragment.newInstance();
                break;
            case R.id.artist_fragment:
                fragment = ArtistFragment.newInstance();
                break;
            case R.id.album_fragment:
                fragment = AlbumFragment.newInstance();
                break;
            case R.id.all_songs_fragment:
                fragment = AllSongsFragment.newInstance();
                break;
            case R.id.playlist_fragment:
                fragment = PlaylistFragment.newInstance();
                break;
            default:
                break;
        }

        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
