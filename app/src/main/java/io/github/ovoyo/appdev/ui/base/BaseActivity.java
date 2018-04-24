package io.github.ovoyo.appdev.ui.base;

import android.support.v4.app.Fragment;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public abstract class BaseActivity extends RxAppCompatActivity implements HasSupportFragmentInjector{

    @Inject
    DispatchingAndroidInjector<Fragment> mDispatchingAndroidInjector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mDispatchingAndroidInjector;
    }
}
