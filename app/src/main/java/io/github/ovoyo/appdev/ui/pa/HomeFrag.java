package io.github.ovoyo.appdev.ui.pa;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseFragment;

public class HomeFrag extends BaseFragment {

    public static HomeFrag get() {
        return new HomeFrag();
    }

    @Override
    protected View onCreateView() {
        FrameLayout layout = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.frag_home, null);
        ButterKnife.bind(this, layout);
        return layout;
    }
}
