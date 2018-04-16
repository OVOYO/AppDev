package io.github.ovoyo.appdev.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.github.ovoyo.appdev.ui.home.HomeFragment;
import io.github.ovoyo.appdev.ui.pa.HomeFrag;
import io.github.ovoyo.appdev.ui.pb.IdeaFrag;
import io.github.ovoyo.appdev.ui.pc.SaveFrag;
import io.github.ovoyo.appdev.ui.pd.MyFrag;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    public abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    public abstract HomeFrag contributeHomeFrag();

    @ContributesAndroidInjector
    public abstract IdeaFrag contributeIdeaFrag();

    @ContributesAndroidInjector
    public abstract SaveFrag contributeSaveFrag();

    @ContributesAndroidInjector
    public abstract MyFrag contributeMyFrag();

}
