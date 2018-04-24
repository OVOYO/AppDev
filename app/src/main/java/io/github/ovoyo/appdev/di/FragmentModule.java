package io.github.ovoyo.appdev.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.github.ovoyo.appdev.ui.BlankFragment;
import io.github.ovoyo.appdev.ui.login.LoginFragment;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    public abstract BlankFragment contributeBlankFragment();

    @ContributesAndroidInjector
    public abstract LoginFragment contributeLoginFragment();

}
