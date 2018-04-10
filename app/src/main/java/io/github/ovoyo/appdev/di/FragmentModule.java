package io.github.ovoyo.appdev.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.github.ovoyo.appdev.ui.BlankFragment;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    public abstract BlankFragment contributeBlankFragment();

}
