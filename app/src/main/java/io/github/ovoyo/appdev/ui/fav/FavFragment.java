package io.github.ovoyo.appdev.ui.fav;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseHomeFragment;


public class FavFragment extends BaseHomeFragment {

    public static final String TAG = "FavFragment";

    public static FavFragment get(){
        return new FavFragment();
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
