package com.digitalidllc.alex_roh.newsreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.digitalidllc.alex_roh.newsreader.common.Constants;
import com.digitalidllc.alex_roh.newsreader.networking.HackerNewsApi;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;

public class NewsListActivity extends AppCompatActivity {
    private HackerNewsApi mHackerNewsApi;

    @BindView(R.id.newsLV) private ListView newsLV;
    private ArrayList<News> newsList = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private SQLiteDatabase newsDatabase;
    private String[] updatedNewsNum = new String[Constants.MAX_NEWS_NUM];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    
    public void onNewsClicked(News news){
        //open up article in Webview
        Intent intent = new Intent(getApplicationContext(), NewsViewerActivity.class);
        intent.putExtra("url",news.getURL());

        startActivity(intent);
    }
}
