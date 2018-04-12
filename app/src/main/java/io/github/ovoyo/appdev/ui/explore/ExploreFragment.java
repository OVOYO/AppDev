package io.github.ovoyo.appdev.ui.explore;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseMainFragment;

public class ExploreFragment extends BaseMainFragment {

    public static ExploreFragment newInstance(){
        return new ExploreFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }
}
