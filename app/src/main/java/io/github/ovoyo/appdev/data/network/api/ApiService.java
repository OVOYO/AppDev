package io.github.ovoyo.appdev.data.network.api;


import java.util.List;

import io.github.ovoyo.appdev.data.network.model.Article;
import io.github.ovoyo.appdev.data.network.model.Gallery;
import io.github.ovoyo.appdev.data.network.model.Timestamp;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String BASE_URL = "https://api.bmob.cn/1/";

    @GET("timestamp")
    Flowable<List<Timestamp>> getServerTimestamp();

    @GET("classes/gallery?where={\"$and\":[{\"showTime\":{showTime}},{\"showType\":{showType}}]}")
    Flowable<List<Gallery>> getGallery(@Path("showTime") String showTime,@Path("showType") int showType);

    @GET("classes/_Article?limit=50&skip={skip}&order=createdAt")
    Flowable<List<Article>> getArticleList(@Query("skip") int skip);
}
