package com.bitsgroove.corona.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitsgroove.corona.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BreathingFragment extends Fragment {
    public CardView[] cv_breath = new CardView[3];
    TextView txt_timer, txt_breath, txt_breathout2, txt_timer2, txt_breathin, txt_breathin2;
    private static final String TAG = "BreathingFragment";
    private volatile boolean stopThread = false;
    private boolean flag = false, stopFlag = true;
    private AdView mAdView;

    public BreathingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_breathing, container, false);
        getActivity().setTitle("Home");

        // Initialise ads
        mAdView = v.findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest); //End

        cv_breath[0] = v.findViewById(R.id.cvBreathIn);
        cv_breath[1] = v.findViewById(R.id.cvCounter);
        cv_breath[2] = v.findViewById(R.id.cvBreathOut);
        txt_timer = v.findViewById(R.id.txtTimer);
        txt_timer2 = v.findViewById(R.id.txtCounter2);
        txt_breath = v.findViewById(R.id.textView20);
        txt_breathout2 = v.findViewById(R.id.textView21);
        txt_breathin2 = v.findViewById(R.id.textView17);
        txt_breathin = v.findViewById(R.id.textView16);

        cv_breath[1].setVisibility(View.GONE);
        cv_breath[2].setVisibility(View.GONE);

        final Counter counter = new Counter();

        cv_breath[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(counter).start();
//                timer();
                txt_breathin.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                txt_timer2.setText("Hold on to your breath");
                txt_breathin2.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                cv_breath[1].setVisibility(View.VISIBLE);
                cv_breath[2].setVisibility(View.VISIBLE);
                cv_breath[0].setEnabled(false);
            }
        });

        cv_breath[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    flag = false;
                    txt_timer.setTextColor(getResources().getColor(R.color.colorBlack, null));
                    txt_breathout2.setTextColor(getResources().getColor(R.color.colorBlack, null));
                    txt_breath.setTextColor(getResources().getColor(R.color.colorBlack, null));
                    cv_breath[2].setBackgroundResource(R.drawable.cv_background);
                    cv_breath[2].setEnabled(true);
                    txt_breathout2.setText("Tap on breathing out before timer ends");
                    txt_timer.setText("10");
                    txt_timer2.setText("");
                    txt_timer2.setText("Hold on to your breath");
                    txt_timer2.setTextColor(getResources().getColor(R.color.colorBlack, null));

                    // Begin counter
                    stopThread = false;
                    stopFlag = true;
//                    timer();
                    new Thread(counter).start();
                }
            }
        });

        cv_breath[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopThread = true;
                cv_breath[2].setBackgroundResource(R.drawable.btn_yes);
                cv_breath[2].setEnabled(false);
                txt_timer.setTextColor(getResources().getColor(R.color.colorRed, null));
                txt_breathout2.setTextColor(getResources().getColor(R.color.colorRed, null));
                txt_breath.setTextColor(getResources().getColor(R.color.colorRed, null));
                txt_timer2.setTextColor(getResources().getColor(R.color.colorRed, null));
                txt_breathout2.setText("Your situation is alarming");
                flag = false;

            }
        });


        return v;
    }

    public void update() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cv_breath[2].setBackgroundResource(R.drawable.btn_no);
                cv_breath[2].setEnabled(false);
                txt_breathout2.setText("You Done Well");
                txt_timer.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                txt_breathout2.setTextColor(getResources().getColor(R.color.colorPrimaryDark, null));
                txt_breath.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                txt_timer.setText("CLEAR");
                txt_timer2.setText("Tap to restart");
            }
        });

    }

    public class Counter implements Runnable {
        int i;
        @Override
        public void run() {

            for (i = 9; i >= 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (stopThread) {
                    stopFlag = false;
                } else {
                    final int val = i;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txt_timer.setText(Integer.toString(val));
                        }
                    });
                }

            }

            if (stopFlag)
                update();
            flag = true;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    txt_timer2.setText("Tap to restart");
                }
            });
        }
    }
}
