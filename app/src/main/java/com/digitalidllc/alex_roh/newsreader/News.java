package com.digitalidllc.alex_roh.newsreader;

/**
 * Created by Alex Roh on 11/4/18.
 **/
public class News {
    private String mTitle;
    private String mURL;

    public News(String title, String URL){
        this.mTitle=title;
        this.mURL=URL;
    }

    public String getTitle(){
        return mTitle;
    }

    private void setTitle(String title){
        this.mTitle=title;
    }

    public String getURL(){
        return mURL;
    }

    private void setURL(String url){
        this.mURL=url;
    }

}
