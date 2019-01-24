package com.example.ricindigus.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsItem>> {

    String URL_REQUEST = "";

    public NewsLoader(Context context, String url) {
        super(context);
        this.URL_REQUEST = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsItem> loadInBackground() {
        if (URL_REQUEST == null) return null;

        List<NewsItem> noticias = HttpUtils.fetchDataNews(URL_REQUEST);

        return noticias;
    }
}
