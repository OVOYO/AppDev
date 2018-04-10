package io.github.ovoyo.appdev.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import io.github.ovoyo.appdev.AppDevApp;

@Singleton
@Component(modules = {AndroidInjectionModule.class,AppModule.class, ActivityModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

    void inject(AppDevApp appDevApp);

}
