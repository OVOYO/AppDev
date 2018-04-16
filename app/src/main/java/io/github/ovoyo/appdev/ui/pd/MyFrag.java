package io.github.ovoyo.appdev.ui.pd;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseFragment;

public class MyFrag extends BaseFragment {

    public static MyFrag get(){
        return new MyFrag();
    }

    @Override
    protected View onCreateView() {
        FrameLayout layout = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.frag_my, null);
        ButterKnife.bind(this, layout);
        return layout;
    }
}
