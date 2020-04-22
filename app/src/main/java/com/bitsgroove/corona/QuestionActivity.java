package com.bitsgroove.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bitsgroove.corona.fragment.FluFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class QuestionActivity extends AppCompatActivity {
//    private AdView mAdView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        setTitle("Questionnaire");

        // Initialise Ads
//        mAdView2 = findViewById(R.id.adView2);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView2.loadAd(adRequest); //End

        getSupportFragmentManager().beginTransaction().add(R.id.frameQst, new FluFragment(), "qst").commit();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(QuestionActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
