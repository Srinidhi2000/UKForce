package com.example.android.ukforce;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class crimeoutcomeloader extends AsyncTaskLoader<arrayoutcomes> {
    String url;

    public crimeoutcomeloader(Context context,String givenurl) {
       super(context);
       url=givenurl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public arrayoutcomes loadInBackground() {
        if(url==null)
            return null;
        arrayoutcomes crimes=Connect.fetchcrimeoutcomedata(url);
        return crimes;
    }
}
