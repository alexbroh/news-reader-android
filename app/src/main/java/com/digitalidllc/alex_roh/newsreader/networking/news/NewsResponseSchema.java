package com.digitalidllc.alex_roh.newsreader.networking.news;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

/**
 * Created by Alex Roh on 11/26/18.
 **/
public class NewsResponseSchema  {
    @SerializedName("title")
    private final String mTitle;

    @SerializedName("url")
    private final String mUrl;

    public NewsResponseSchema(String title, String url) {
        mTitle = title;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }
}
