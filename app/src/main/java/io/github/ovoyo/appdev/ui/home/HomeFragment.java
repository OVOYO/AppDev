package io.github.ovoyo.appdev.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseHomeFragment;


public class HomeFragment extends BaseHomeFragment {

    public static final String TAG = "HomeFragment";

    public static HomeFragment get(){
        return new HomeFragment();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return null;
    }

    @Override
    public void setup(View view) {

    }

    @Override
    public void loadData(boolean clear) {

    }

}
