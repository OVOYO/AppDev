package io.github.ovoyo.appdev.ui.pc;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseFragment;

public class SaveFrag extends BaseFragment {

    public static SaveFrag get(){
        return new SaveFrag();
    }

    @Override
    protected View onCreateView() {
        FrameLayout layout = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.frag_save, null);
        ButterKnife.bind(this, layout);
        return layout;
    }
}
