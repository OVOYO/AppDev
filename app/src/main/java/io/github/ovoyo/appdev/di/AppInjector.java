package io.github.ovoyo.appdev.di;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;
import io.github.ovoyo.appdev.AppDevApp;

public class AppInjector {

    private AppInjector() {
    }

    public static void init(AppDevApp appDevApp){
        // Dagger2 init
        DaggerAppComponent.builder().application(appDevApp).build().inject(appDevApp);

        appDevApp.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                handleActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private static void handleActivity(Activity activity){
        if (activity instanceof HasSupportFragmentInjector){
            AndroidInjection.inject(activity);
        }

        if (activity instanceof FragmentActivity){
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            fragmentActivity.getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
                    if (f instanceof Injectable){
                        AndroidSupportInjection.inject(f);
                    }
                }
            },true);
        }
    }
}
