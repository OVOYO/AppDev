package io.github.ovoyo.appdev.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.github.ovoyo.appdev.ui.BlankFragment;
import io.github.ovoyo.appdev.ui.explore.ExploreFragment;
import io.github.ovoyo.appdev.ui.home.HomeFragment;
import io.github.ovoyo.appdev.ui.me.MeFragment;
import io.github.ovoyo.appdev.ui.notify.NotifyFragment;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    public abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    public abstract ExploreFragment contributeExploreFragment();

    @ContributesAndroidInjector
    public abstract NotifyFragment contributeNotifyFragment();

    @ContributesAndroidInjector
    public abstract MeFragment contributeMeFragment();
}
