package com.bitsgroove.corona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.bitsgroove.corona.fragment.BreathingFragment;
import com.bitsgroove.corona.fragment.NoticeFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class HomeActivity extends AppCompatActivity {

//    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("Dashboard");

        // Initialise ads
//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest); //End

        getSupportFragmentManager().beginTransaction().add(R.id.frameHome, new BreathingFragment(), "breath").commit();

        // Bottom Nav
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.getMenu().getItem(1).setChecked(true);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    // Bottom nav
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.Setting:
                            getSupportFragmentManager().beginTransaction().replace(R.id.frameHome,
                                    new NoticeFragment(), "notification").commit();
                            break;
                        case R.id.home:
                            getSupportFragmentManager().beginTransaction().replace(R.id.frameHome,
                                    new BreathingFragment(), "breath").commit();
                            break;
                        case R.id.qst:
                            Intent intent = new Intent(HomeActivity.this, QuestionActivity.class);
                            startActivity(intent);
                            break;
                    }

                    return true;
                }
    };

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
