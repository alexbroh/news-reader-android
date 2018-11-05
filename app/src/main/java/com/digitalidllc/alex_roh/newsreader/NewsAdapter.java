package com.digitalidllc.alex_roh.newsreader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex Roh on 11/4/18.
 **/
public class NewsAdapter extends ArrayAdapter<News> {
    private Context mContext;
    private List<News>  newsList;

    public NewsAdapter(@NonNull Context context, ArrayList<News> list) {
        super(context, 0, list);
        this.mContext = context;
        this.newsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.news_item,parent,false);

        News currentNews = newsList.get(position);

        TextView title = (TextView) listItem.findViewById(R.id.newsTV);
        title.setText(currentNews.getTitle());

        return listItem;
    }
}
