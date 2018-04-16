package io.github.ovoyo.appdev.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseActivity;
import io.github.ovoyo.appdev.ui.base.BaseMainFragment;
import io.github.ovoyo.appdev.ui.explore.ExploreFragment;
import io.github.ovoyo.appdev.ui.home.HomeFragment;
import io.github.ovoyo.appdev.ui.me.MeFragment;
import io.github.ovoyo.appdev.ui.notify.NotifyFragment;
import io.github.ovoyo.appdev.utils.StatusBarHelper;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity implements BaseMainFragment.OnBackToFirstListener, HomeFragment.OnBottomNavBarShowHide {

    private static final String TAG = "MainActivity";

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private SupportFragment[] mFragments = new SupportFragment[4];

    @BindView(R.id.home_bottom_bar)
    BottomNavigationBar mBottomNavBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        setUnbinder(ButterKnife.bind(this));

        init();
        StatusBarHelper.translucent(this,getResources().getColor(R.color.navy_blue_dark));
//        StatusBarHelper.setStatusBarLightMode(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }


    private void init() {

        SupportFragment supportFragment = findFragment(HomeFragment.class);
        if (supportFragment == null) {

            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = NotifyFragment.newInstance();
            mFragments[THIRD] = ExploreFragment.newInstance();
            mFragments[FOURTH] = MeFragment.newInstance();

            loadMultipleRootFragment(R.id.home_fl_container, FIRST, mFragments);
        } else {

            mFragments[FIRST] = supportFragment;
            mFragments[SECOND] = findFragment(NotifyFragment.class);
            mFragments[THIRD] = findFragment(ExploreFragment.class);
            mFragments[FOURTH] = findFragment(MeFragment.class);
        }

        mBottomNavBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_black_24dp, getString(R.string.bottom_nav_home)))
                .addItem(new BottomNavigationItem(R.drawable.ic_explore_black_24dp, getString(R.string.bottom_nav_explore)))
                .addItem(new BottomNavigationItem(R.drawable.ic_notifications_black_24dp, getString(R.string.bottom_nav_notify)))
                .addItem(new BottomNavigationItem(R.drawable.ic_person_black_24dp, getString(R.string.bottom_nav_me)))
                .initialise();
        mBottomNavBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                showHideFragment(mFragments[position]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

                Log.e(TAG, "onTabReselected: " + position );
                if (position == FIRST) {
                    SupportFragment curFrag = mFragments[position];
                    int count = curFrag.getChildFragmentManager().getBackStackEntryCount();
                    Log.e(TAG, "onTabReselected: " + count );
                    if (count == 0) {
                        HomeFragment homeFragment = (HomeFragment) curFrag;
                        homeFragment.onTabSelected();
                    }
                }

            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    public void onBackToFirstFragment() {
        mBottomNavBar.selectTab(0);
    }

    @Override
    public void show() {
        mBottomNavBar.show();
    }

    @Override
    public void hide() {
        mBottomNavBar.hide();
    }
}
