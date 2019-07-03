package com.example.android.ukforce.force_details;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.android.ukforce.Connect;

import java.util.List;

public class forceloader extends AsyncTaskLoader<List<Force>> {
    private String url;
    public forceloader(Context context, String givenurl) {
        super(context);
        url=givenurl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Force> loadInBackground() {
        if(url==null)
        return null;
        List<Force> forces=Connect.fetchdata(url);
        return forces;
    }
}
