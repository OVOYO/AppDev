package io.github.ovoyo.appdev.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseActivity;
import io.github.ovoyo.appdev.ui.base.FragmentContract;
import io.github.ovoyo.appdev.ui.fav.FavFragment;
import io.github.ovoyo.appdev.ui.home.HomeFragment;
import io.github.ovoyo.appdev.ui.latter.LatterFragment;
import io.github.ovoyo.appdev.ui.login.LoginFragment;

public class MainActivity
        extends BaseActivity
        implements FragmentContract.OnFragmentBack{

    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    CircleImageView mUserIcon;

    private List<Fragment> mFragmentList = new ArrayList<>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupTitle();

        setupFragments();
    }

    private void setupFragments(){
        FragmentManager manager = getSupportFragmentManager();
        if (mFragmentList.isEmpty()){
            mFragmentList.add(HomeFragment.get());
            mFragmentList.add(LatterFragment.get());
            mFragmentList.add(FavFragment.get());
        }
        manager
                .beginTransaction()
                .add(R.id.main_content_fl,mFragmentList.get(0),HomeFragment.TAG)
                .add(R.id.main_content_fl,mFragmentList.get(1),LatterFragment.TAG)
                .add(R.id.main_content_fl,mFragmentList.get(2),FavFragment.TAG)
                .commit();

        mNavigationView.setCheckedItem(R.id.nav_home);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(getString(R.string.nav_home));
    }

    private void setupTitle() {
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mUserIcon = mNavigationView.getHeaderView(0).findViewById(R.id.user_icon);
        mUserIcon.setOnClickListener(v -> {
            LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag(LoginFragment.TAG);
            if (loginFragment == null){
                loginFragment = new LoginFragment();
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.drawer_layout,loginFragment,LoginFragment.TAG)
                    .addToBackStack(LoginFragment.TAG)
                    .commit();

            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        });

        mNavigationView.setNavigationItemSelectedListener(item -> {

            ActionBar actionBar = getSupportActionBar();
            FragmentManager manager = getSupportFragmentManager();

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                manager
                        .beginTransaction()
                        .show(mFragmentList.get(0))
                        .hide(mFragmentList.get(1))
                        .hide(mFragmentList.get(2))
                        .commit();
                if (actionBar != null)
                actionBar.setTitle(getString(R.string.nav_home));
            } else if (id == R.id.nav_history) {

                manager
                        .beginTransaction()
                        .hide(mFragmentList.get(0))
                        .show(mFragmentList.get(1))
                        .hide(mFragmentList.get(2))
                        .commit();
                if (actionBar != null)
                    actionBar.setTitle(getString(R.string.nav_history));
            } else if (id == R.id.nav_fav) {

                manager
                        .beginTransaction()
                        .hide(mFragmentList.get(0))
                        .hide(mFragmentList.get(1))
                        .show(mFragmentList.get(2))
                        .commit();
                if (actionBar != null)
                    actionBar.setTitle(getString(R.string.nav_fav));
            } else if (id == R.id.nav_settings) {

            } else if (id == R.id.nav_share) {

            } else if (id == R.id.nav_send) {

            }

            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavBack(String tag) {
        Log.e(TAG, "onNavBack: " + tag );
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag(LoginFragment.TAG);
        if (loginFragment != null){
            getSupportFragmentManager().popBackStack(LoginFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

}
