package io.github.ovoyo.appdev.ui;

import android.os.Bundle;

import io.github.ovoyo.appdev.R;
import io.github.ovoyo.appdev.ui.base.BaseActivity;
import io.github.ovoyo.appdev.ui.base.BaseFragment;
import io.github.ovoyo.appdev.ui.home.HomeFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected int getContextViewId() {
        return R.id.poe_app;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {

            BaseFragment fragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }

}
