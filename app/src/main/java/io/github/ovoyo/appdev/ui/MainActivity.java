package io.github.ovoyo.appdev.ui;

import android.os.Bundle;

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
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity implements BaseMainFragment.OnBackToFirstListener {

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

            }
        });
    }

    @Override
    public void onBackToFirstFragment() {
        mBottomNavBar.selectTab(0);
    }
}
