package com.digitalidllc.alex_roh.newsreader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

        UpdatedNewsDownloader updatedNewsDownloader = new UpdatedNewsDownloader();
        Boolean updatedNewsSuccess=false;
        try{
           updatedNewsSuccess = updatedNewsDownloader.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty").get();
        } catch(Exception e){
            e.printStackTrace();
        }

        Log.i("updatedNewsSuccess", Boolean.toString(updatedNewsSuccess));
    }


}
