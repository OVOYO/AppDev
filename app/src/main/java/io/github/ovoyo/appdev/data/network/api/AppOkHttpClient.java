package io.github.ovoyo.appdev.data.network.api;


import okhttp3.OkHttpClient;
import okhttp3.Request;

public class AppOkHttpClient {

    public static OkHttpClient getOkHttpClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("X-Bmob-Application-Id","bf43dd19d5df4d553b420a56319c3860")
                            .addHeader("X-Bmob-REST-API-Key","c3f94743fe4a08ffe39c5879ef1ee3ef")
                            .build();

                    return chain.proceed(request);
                })
                .build();
    }
}
