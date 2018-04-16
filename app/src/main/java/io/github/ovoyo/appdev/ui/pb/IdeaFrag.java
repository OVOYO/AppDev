package io.github.ovoyo.appdev.ui.pb;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseFragment;

public class IdeaFrag extends BaseFragment {

    public static IdeaFrag get(){
        return new IdeaFrag();
    }

    @Override
    protected View onCreateView() {
        FrameLayout layout = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.frag_idea, null);
        ButterKnife.bind(this, layout);
        return layout;
    }
}
