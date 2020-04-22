package com.bitsgroove.corona.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitsgroove.corona.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SymptomFragment extends Fragment {
    int val;
    TextView txt_msg;
//    Button btn_safe;
    ImageView img_symptom;
    private AdView mAdView4;
    public SymptomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_symptom, container, false);
        getActivity().setTitle("Symptom");

        val = getArguments().getInt("value");
        txt_msg = v.findViewById(R.id.txtSymptom);
//        btn_safe = v.findViewById(R.id.btnSafe);
        img_symptom = v.findViewById(R.id.imgSymptom);

        // Initialise ads
        mAdView4 = v.findViewById(R.id.adView6);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView4.loadAd(adRequest); //End

        if (val == 0) {
            txt_msg.setText("You Are Safe");
            v.getRootView().setBackgroundResource(R.mipmap.nosymptom);
            txt_msg.setBackgroundResource(R.drawable.safe_txt);
            img_symptom.setImageResource(R.drawable.ic_tick);
        } else {
            txt_msg.setText("You Have \n The Symptoms");
            txt_msg.setBackgroundResource(R.drawable.unsafe_txt);
            v.getRootView().setBackgroundResource(R.mipmap.symptom);
            img_symptom.setImageResource(R.drawable.ic_warning);
        }
//
//        btn_safe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().beginTransaction().replace(R.id.frameFrag, new HelpdashFragment(), "help").addToBackStack(null).commit();
//            }
//        });

        return v;
    }
}
