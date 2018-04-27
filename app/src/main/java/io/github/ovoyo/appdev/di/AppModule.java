package io.github.ovoyo.appdev.di;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.ovoyo.appdev.data.DataManager;
import io.github.ovoyo.appdev.data.network.api.ApiService;
import io.github.ovoyo.appdev.data.network.api.AppOkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    @Singleton
    DataManager provideDataManager(){
        return new DataManager(DataManager.TAG);
    }

    @Provides
    @Singleton
    ApiService provideApiService(){
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                .baseUrl(ApiService.BASE_URL)
                .client(AppOkHttpClient.getOkHttpClient())
                .build()
                .create(ApiService.class);
    }
}
