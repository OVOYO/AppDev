package io.github.ovoyo.appdev.ui.base;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import butterknife.Unbinder;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import me.yokeyword.fragmentation.SupportActivity;


public abstract class BaseActivity extends SupportActivity implements HasSupportFragmentInjector{

    @Inject
    DispatchingAndroidInjector<Fragment> mDispatchingAndroidInjector;

    private Unbinder mUnbinder;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mDispatchingAndroidInjector;
    }

    public void setUnbinder(Unbinder unbinder) {
        mUnbinder = unbinder;
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
        super.onDestroy();
    }
}
