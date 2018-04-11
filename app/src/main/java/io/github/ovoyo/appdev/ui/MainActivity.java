package io.github.ovoyo.appdev.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseActivity;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.nav_view)
    NavigationView mNavigation;

    CircleImageView mUserIcon;
    TextView mUserNickName;
    TextView mUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUnbinder(ButterKnife.bind(this));

        init();

        testFlowable();
    }

    public void testFlowable(){
        Flowable
                .create((FlowableOnSubscribe<String>) emitter -> {

                    Log.e(TAG, "A");
                    emitter.onNext("A");

                    Log.e(TAG, "B");
                    emitter.onNext("B");

                    Log.e(TAG, "C");
                    emitter.onNext("C");

                    for (int i = 0; i < 127; i++) {
                        emitter.onNext(String.valueOf(i));
                    }

                    emitter.onComplete();

                }, BackpressureStrategy.ERROR)
                .subscribe(new FlowableSubscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
//                        s.request(0L);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, s);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void init() {

        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        setNav();
    }

    private void setNav() {
        View headerView = mNavigation.getHeaderView(0);
        mUserIcon = headerView.findViewById(R.id.user_icon);
        mUserNickName = headerView.findViewById(R.id.user_nick_name);
        mUserEmail = headerView.findViewById(R.id.user_email);

        mNavigation.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_camera) {
                // Handle the camera action
            } else if (id == R.id.nav_gallery) {

            } else if (id == R.id.nav_slideshow) {

            } else if (id == R.id.nav_manage) {

            } else if (id == R.id.nav_share) {

            } else if (id == R.id.nav_send) {

            }

            mDrawer.closeDrawer(GravityCompat.START);
            return true;
        });
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

}
