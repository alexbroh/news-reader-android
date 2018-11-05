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

import org.json.JSONObject;

import java.io.BufferedReader;
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
                    if(current!=' '&&current!='['&&current!=']'&&current!=',') result.append(current);
                    else if(current==','){ //append to updatedNewsNum
                        updatedNewsNum[updatedNewsNumItr] = result.toString();
                        Log.i("updatedNewsNum", result.toString());
                        updatedNewsNumItr++;
                        result = new StringBuilder();
                    }
                    data = reader.read();
                }
                return true;
            } catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }

    private class NewsDownloader extends AsyncTask<String, Void, Boolean>{
        @Override
        protected Boolean doInBackground(String... urls) {
            try{
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer(1024);
                String tmp = "";

                while((tmp = reader.readLine()) != null)
                    json.append(tmp).append("\n");
                reader.close();

                JSONObject jsonObject = new JSONObject(json.toString());

                String newsInfo = jsonObject.toString();
                Log.i("NewsInfo", newsInfo);

                News newNews = new News(jsonObject.getString("title"), jsonObject.getString("url"));
                newsList.add(newNews);

                return true;

            } catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpApp();
    }

    private void setUpApp(){
        //methods have to be in this particular order
        wireUIElements();
        updateNewsList();

         //setUpDatabase();
        setUpNewsList();
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

    private void setUpDatabase() {
        for(int i=0; i<updatedNewsNum.length;++i) {
            try {
                NewsDownloader newsDownloader = new NewsDownloader();
                Boolean newsDownloadSuccess = newsDownloader.execute("https://hacker-news.firebaseio.com/v0/item/"+updatedNewsNum[i]+".json?print=pretty").get();
                Log.i("newsDownloadSuccess", Boolean.toString(newsDownloadSuccess));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        newsDatabase = this.openOrCreateDatabase("News", MODE_PRIVATE, null);
        newsDatabase.execSQL("DROP TABLE news");
        newsDatabase.execSQL("CREATE TABLE IF NOT EXISTS news (title VARCHAR(1024), url VARCHAR(1024))");

        newsDatabase.execSQL("INSERT INTO news (title,url) VALUES ('EventStore: Open-Source, Functional Database with Complex Event Processing in JS','https://github.com/eventstore/eventstore')");
    }

    private void setUpNewsList(){
        //populate list
        newsAdapter = new NewsAdapter(this, newsList);
        newsLV.setAdapter(newsAdapter);

        newsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                
            }
        });

    }
}
