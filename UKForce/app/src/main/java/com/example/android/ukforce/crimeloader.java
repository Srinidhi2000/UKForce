package com.example.android.ukforce;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;

import java.util.List;

public class crimeloader extends AsyncTaskLoader<List<crime>> {
    private String url;
    public crimeloader(@NonNull Context context,String givenurl) {
        super(context);
        url=givenurl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<crime> loadInBackground() {
        if(url==null)
            return null;
        List<crime> crimes=Connect.fetchcrimedata(url);
        return crimes;
    }
}
