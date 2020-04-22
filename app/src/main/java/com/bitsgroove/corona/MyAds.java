package com.bitsgroove.corona;

import android.app.Application;

import com.bitsgroove.corona.R;
import com.google.android.gms.ads.MobileAds;

public class MyAds extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(this, getString(R.string.app_id));
    }
}
