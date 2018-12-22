package com.digitalidllc.alex_roh.newsreader.screens.newslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.digitalidllc.alex_roh.newsreader.News;
import com.digitalidllc.alex_roh.newsreader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Roh on 12/21/18.
 **/
public class NewsListViewMvcImpl implements NewsAdapter.OnNewsClickListener, NewsListViewMvc {

    private ListView mNewsLV;
    private ArrayList<News> mNewsList = new ArrayList<>();
    private NewsAdapter mNewsAdapter;

    private final View mRootView;

    private final List<Listener> mListeners = new ArrayList<>(1);

    public NewsListViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        mRootView = inflater.inflate(R.layout.activity_main, parent, false);

        mNewsLV = findViewById(R.id.newsLV);

        mNewsAdapter = new NewsAdapter(getContext(),this, mNewsList);
        mNewsLV.setAdapter(mNewsAdapter);
    }

    @Override
    public void registerListener(Listener listener) {
        mListeners.add(listener);
    }

    @Override
    public void unregisterListener(Listener listener) {
        mListeners.remove(listener);
    }

    private Context getContext() {
        return getRootView().getContext();
    }

    private <T extends View> T findViewById(int id) {
        return getRootView().findViewById(id);
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public void onNewsClicked(News news) {
        for(Listener listener : mListeners) {
            listener.onNewsClicked(news);
        }
    }

    @Override
    public void bindNews(String title, String url) {
        mNewsAdapter.notifyDataSetChanged();
        mNewsList.add(new News(title,url));
    }
}

