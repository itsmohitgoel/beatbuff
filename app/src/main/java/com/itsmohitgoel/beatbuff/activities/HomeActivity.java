package com.itsmohitgoel.beatbuff.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.itsmohitgoel.beatbuff.R;
import com.itsmohitgoel.beatbuff.fragments.CategoryListFragment;
import com.itsmohitgoel.beatbuff.fragments.CategoryListFragment.MusicCategory;
import com.itsmohitgoel.beatbuff.fragments.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeActivity extends SingleFragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

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

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem searchItem = menu.findItem(R.id.search_menu_item);
        SearchView searchView = (SearchView) searchItem.getActionView();

        ImageView searchButtonImageView = (ImageView) searchView
                .findViewById(android.support.v7.appcompat.R.id.search_button);
        searchButtonImageView.setImageResource(R.drawable.ic_search);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_menu_item) {
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
                fragment = CategoryListFragment.newInstance(MusicCategory.GENRE);
                break;
            case R.id.artist_fragment:
                fragment = CategoryListFragment.newInstance(MusicCategory.ARTIST);
                break;
            case R.id.album_fragment:
                fragment = CategoryListFragment.newInstance(MusicCategory.ALBUMS);
                break;
            case R.id.all_songs_fragment:
                fragment = CategoryListFragment.newInstance(MusicCategory.ALL_SONGS);
                break;
            default:
                break;
        }

        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
