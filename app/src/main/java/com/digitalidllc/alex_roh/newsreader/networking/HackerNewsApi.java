package com.digitalidllc.alex_roh.newsreader.networking;

import com.digitalidllc.alex_roh.newsreader.networking.news.NewsResponseSchema;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Alex Roh on 11/26/18.
 **/
public interface HackerNewsApi {
    @GET("v0/topstories.json?print=pretty")
    Call<List<Integer>> getTopStories();

    @GET("v0/item/{articleid}.json?print=pretty")
    Call<NewsResponseSchema> getArticle(@Path("articleid") int id);
}
