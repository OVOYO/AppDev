package io.github.ovoyo.appdev.ui.pb;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseFragment;
import io.github.ovoyo.appdev.ui.pb.sub.SubFrag1;
import io.github.ovoyo.appdev.ui.pb.sub.SubFrag2;
import io.github.ovoyo.appdev.ui.pb.sub.SubFrag3;

public class IdeaFrag extends BaseFragment {

    @BindView(R.id.idea_tabs)
    TabLayout mTabs;
    @BindView(R.id.idea_view_pager)
    ViewPager mViewPager;

    public static IdeaFrag get(){
        return new IdeaFrag();
    }

    @Override
    protected View onCreateView() {
        FrameLayout layout = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.frag_idea, null);
        ButterKnife.bind(this, layout);

        setupViewPager(mViewPager);
        mTabs.setupWithViewPager(mViewPager);

        return layout;
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(SubFrag1.get(), "Sub 1");
        adapter.addFragment(SubFrag2.get(), "Sub 2");
        adapter.addFragment(SubFrag3.get(), "Sub 3");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        Adapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
