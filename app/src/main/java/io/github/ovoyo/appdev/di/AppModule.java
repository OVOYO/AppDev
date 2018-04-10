package io.github.ovoyo.appdev.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.ovoyo.appdev.data.DataManager;

@Module
public class AppModule {

    @Provides
    @Singleton
    DataManager provideDataManager(){
        return new DataManager(DataManager.TAG);
    }
}
