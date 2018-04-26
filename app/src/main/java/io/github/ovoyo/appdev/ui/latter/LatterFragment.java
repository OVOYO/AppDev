package io.github.ovoyo.appdev.ui.latter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseHomeFragment;


public class LatterFragment extends BaseHomeFragment {

    public static final String TAG = "LatterFragment";

    public static LatterFragment get() {
        return new LatterFragment();
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