package com.digitalidllc.alex_roh.newsreader.screens.newslist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.digitalidllc.alex_roh.newsreader.News;
import com.digitalidllc.alex_roh.newsreader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Roh on 11/4/18.
 **/
public class NewsAdapter extends ArrayAdapter<News> {
    private final Context mContext;
    private final List<News>  mNewsList;

    private final OnNewsClickListener mOnNewsClickListener;

    public interface OnNewsClickListener {
        void onNewsClicked(News news);
    }

    public NewsAdapter(@NonNull Context context, OnNewsClickListener onNewsClickListener, @NonNull ArrayList<News> list) {
        super(context, 0, list);
        mContext = context;
        mNewsList = list;
        mOnNewsClickListener = onNewsClickListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.news_item,parent,false);

        News currentNews = mNewsList.get(position);

        //bind data
        TextView title = (TextView) listItem.findViewById(R.id.newsTV);
        title.setText(currentNews.getTitle());

        //set listener
        listItem.setOnClickListener((view) -> { mOnNewsClickListener.onNewsClicked(currentNews); });

        return listItem;
    }
}
