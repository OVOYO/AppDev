package io.github.ovoyo.appdev;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import cn.bmob.v3.Bmob;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.github.ovoyo.appdev.di.AppInjector;


public class AppDevApp extends Application implements HasActivityInjector{

    private static final String BMOB_APP_KEY = "bf43dd19d5df4d553b420a56319c3860";

    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        AppInjector.init(this);

        Bmob.initialize(this,BMOB_APP_KEY);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mDispatchingAndroidInjector;
    }
}
