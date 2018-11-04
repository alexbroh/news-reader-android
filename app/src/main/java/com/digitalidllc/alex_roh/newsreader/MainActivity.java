package com.digitalidllc.alex_roh.newsreader;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView newsLV;
    private final int MAX_NEWS_NUM = 10;
    private ArrayList<News> newsList = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private SQLiteDatabase newsDatabase;
    private String[] updatedNewsNum = new String[MAX_NEWS_NUM];

    private class UpdatedNewsDownloader extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            HttpURLConnection httpURLConnection = null;
            try{
                URL url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                int updatedNewsNumItr = 0;
                while(data != -1 && updatedNewsNumItr<MAX_NEWS_NUM){
                    char current = (char) data;
                   // Log.i("currentChar", Character.toString(current));

                    if(current!=' '&&current!='['&&current!=']'&&current!=',') result.append(current);
                    else if(current==','){ //append to updatedNewsNum
                        updatedNewsNum[updatedNewsNumItr] = result.toString();
                        Log.i("updatedNewsNum", result.toString());
                        updatedNewsNumItr++;
                        result = new StringBuilder();
                    }
                  //  Log.i("updatedNewsNum", Integer.toString(updatedNewsNumItr));

                    data = reader.read();
                }
                return true;
            } catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }

    private class NewsDownloader extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection httpURLConnection = null;

            try{

            } catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        News test = new News("title","hello");
        newsList.add(test);
        setUpApp();
    }

    private void setUpApp(){
        //methods have to be in this particular order
        wireUIElements();
        updateNewsList();
        setUpNewsList();
        setUpDatabase();
    }

    private void wireUIElements(){
        newsLV = findViewById(R.id.newsLV);
    }

    private void updateNewsList(){
        UpdatedNewsDownloader updatedNewsDownloader = new UpdatedNewsDownloader();
        Boolean updatedNewsDownloadSuccess=false;
        try{
            updatedNewsDownloadSuccess = updatedNewsDownloader.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty").get();
        } catch(Exception e){
            e.printStackTrace();
        }

        Log.i("NewsDownloadSuccess", Boolean.toString(updatedNewsDownloadSuccess));
    }

    private void setUpDatabase(){
        newsDatabase = this.openOrCreateDatabase("News", MODE_PRIVATE, null);
        newsDatabase.execSQL("CREATE TABLE IF NOT EXISTS news (title VARCHAR(256), url VARCHAR(256))");

    }

    private void setUpNewsList(){
        newsAdapter = new NewsAdapter(this, newsList);
        newsLV.setAdapter(newsAdapter);

        newsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }
}
