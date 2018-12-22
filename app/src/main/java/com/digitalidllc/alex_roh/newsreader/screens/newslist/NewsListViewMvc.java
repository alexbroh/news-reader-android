package com.digitalidllc.alex_roh.newsreader.screens.newslist;

import android.view.View;

import com.digitalidllc.alex_roh.newsreader.News;

/**
 * Created by Alex Roh on 12/21/18.
 **/
interface NewsListViewMvc {

    public interface Listener {
        void onNewsClicked(News news);
    }

    void registerListener(Listener listener);

    void unregisterListener(Listener listener);

    View getRootView();

    void bindNews(String title, String url);
}
