package com.example.hp.offermagnet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;


public class RateFragment extends Fragment {

    public RateFragment() {
        // Required empty public constructor
    }

   RatingBar r;
Button btnRate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_rate, container, false);
        r=(RatingBar)v.findViewById(R.id.offerOfRequest);
        r.getNumStars();
Button btnRate=(Button)v.findViewById(R.id.btnRate);
btnRate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Fragment productDetailFragment = new One();
        FragmentManager ft = getFragmentManager();
        ft.beginTransaction().replace(R.id.frame_offer, productDetailFragment).commit();

    }
});
        return v;

    }





}
