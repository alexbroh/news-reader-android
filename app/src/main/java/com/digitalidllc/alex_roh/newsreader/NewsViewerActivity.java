package com.digitalidllc.alex_roh.newsreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_viewer);

        setUpWebview();
    }

    private void setUpWebview(){
        WebView newsWebView = findViewById(R.id.newsWebView);
        newsWebView.getSettings().setJavaScriptEnabled(true);

        newsWebView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        newsWebView.loadUrl(url);
    }
}
