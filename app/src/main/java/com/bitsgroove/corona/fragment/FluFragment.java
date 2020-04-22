package com.bitsgroove.corona.fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bitsgroove.corona.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FluFragment extends Fragment {

    Button [] btn = new Button[6];
    Boolean [] isSymptom = new Boolean[3];
    int pressCount = 0;
    ConstraintLayout[] cv_symp = new ConstraintLayout[3];
    private AdView mAdView3;

    public FluFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_flu, container, false);
        getActivity().setTitle("Questionnaire");

        btn[0] = v.findViewById(R.id.btnYes);
        btn[1] = v.findViewById(R.id.btnNo);
        btn[2] = v.findViewById(R.id.btnYes12);
        btn[3] = v.findViewById(R.id.btnNo12);
        btn[4] = v.findViewById(R.id.btnYes13);
        btn[5] = v.findViewById(R.id.btnNo13);
        cv_symp[0] = v.findViewById(R.id.clSymp);
        cv_symp[1] = v.findViewById(R.id.clSymp2);
        cv_symp[2] = v.findViewById(R.id.clSymp3);

        // Initialise ads
        mAdView3 = v.findViewById(R.id.adView5);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView3.loadAd(adRequest); //End

        btn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn[0].setEnabled(false);
                btn[1].setEnabled(false);
                isSymptom[0] = true;
                ++pressCount;
                cv_symp[0].setBackgroundResource(R.drawable.cv_yes);
                checkCount();
            }
        });
        btn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn[0].setEnabled(false);
                btn[1].setEnabled(false);
                isSymptom[0] = false;
                ++pressCount;
                cv_symp[0].setBackgroundResource(R.drawable.cv_no);
                checkCount();
            }
        });
        btn[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn[2].setEnabled(false);
                btn[3].setEnabled(false);
                isSymptom[1] = true;
                ++pressCount;
                cv_symp[1].setBackgroundResource(R.drawable.cv_yes);
                checkCount();
            }
        });
        btn[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn[2].setEnabled(false);
                btn[3].setEnabled(false);
                isSymptom[1] = false;
                ++pressCount;
                cv_symp[1].setBackgroundResource(R.drawable.cv_no);
                checkCount();
            }
        });
        btn[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn[4].setEnabled(false);
                btn[5].setEnabled(false);
                isSymptom[2] = true;
                ++pressCount;
                cv_symp[2].setBackgroundResource(R.drawable.cv_yes);
                checkCount();
            }
        });
        btn[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn[4].setEnabled(false);
                btn[5].setEnabled(false);
                isSymptom[2] = false;
                ++pressCount;
                cv_symp[2].setBackgroundResource(R.drawable.cv_no);
                checkCount();
            }
        });

        return v;
    }

    private void checkCount() {
        if (pressCount == 3) {
            int val = 0;
            if (isSymptom[0] && isSymptom[1] && isSymptom[2] )
                val = 1;
            else if (isSymptom[1] && isSymptom[2])
                val = 1;
            else if (isSymptom[0] && isSymptom[2])
                val = 1;
            Fragment fragment = new SymptomFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("value", val);
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.frameQst, fragment, "symptom").commit();
        }
    }

}
