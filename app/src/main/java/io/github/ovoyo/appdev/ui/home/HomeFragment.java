package io.github.ovoyo.appdev.ui.home;


import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseFragment;
import io.github.ovoyo.appdev.ui.pa.HomeFrag;
import io.github.ovoyo.appdev.ui.pc.SaveFrag;


public class HomeFragment extends BaseFragment {

    public static final String TAG = "HomeFrag";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.bottom_bar)
    BottomNavigationBar mBottomNavigationBar;

    private HomePageAdapter mPageAdapter;

    public HomeFragment get() {
        return new HomeFragment();
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    protected View onCreateView() {
        CoordinatorLayout layout = (CoordinatorLayout) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, layout);
        initToolbar();
        initViewPager();
        initBottomNavBar();
        return layout;
    }

    private void initToolbar() {
        mToolbar.setTitle(R.string.app_name);
        mToolbar.inflateMenu(R.menu.main);
        mToolbar.setOnMenuItemClickListener(item -> {

            switch (item.getItemId()) {
                case R.id.action_search:
                    return true;
                case R.id.action_plus:
                    return true;
            }
            return false;
        });
    }

    private void initViewPager() {
        if (mPageAdapter == null) {
            mPageAdapter = new HomePageAdapter(getChildFragmentManager());
        }
        mPager.setAdapter(mPageAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomNavigationBar.selectTab(position, false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initBottomNavBar() {
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_black_24dp, R.string.bottom_nav_home))
                .addItem(new BottomNavigationItem(R.drawable.ic_all_inclusive_black_24dp, R.string.bottom_nav_idea))
                .addItem(new BottomNavigationItem(R.drawable.ic_favorite_black_24dp, R.string.bottom_nav_save))
                .addItem(new BottomNavigationItem(R.drawable.ic_person_black_24dp, R.string.bottom_nav_my))
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                mPager.setCurrentItem(position, false);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                if (position == 0) {
                    BaseFragment fragment = mPageAdapter.getFragments().get(position);
                    HomeFrag homeFrag = (HomeFrag) fragment;
                    homeFrag.onTabSelected();
                }else if (position == 2){
                    BaseFragment baseFragment = mPageAdapter.getFragments().get(position);
                    SaveFrag saveFrag = (SaveFrag) baseFragment;
                    saveFrag.onTabSelected();
                }
            }
        });
    }

}
