package io.github.ovoyo.appdev.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.github.ovoyo.appdev.ui.MainActivity;
import io.github.ovoyo.appdev.ui.detail.DetailActivity;

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivity();


    @ContributesAndroidInjector(modules = DetailModule.class)
    abstract DetailActivity contributeDetailActivity();
}
