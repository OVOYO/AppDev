package io.github.ovoyo.appdev.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Arrays;
import java.util.List;

import io.github.ovoyo.appdev.ui.base.BaseFragment;
import io.github.ovoyo.appdev.ui.pa.HomeFrag;
import io.github.ovoyo.appdev.ui.pb.IdeaFrag;
import io.github.ovoyo.appdev.ui.pc.SaveFrag;
import io.github.ovoyo.appdev.ui.pd.MyFrag;


public class HomePageAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments = Arrays.asList(
            HomeFrag.get(),
            IdeaFrag.get(),
            SaveFrag.get(),
            MyFrag.get()
    );

    public List<BaseFragment> getFragments() {
        return mFragments;
    }

    public HomePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
