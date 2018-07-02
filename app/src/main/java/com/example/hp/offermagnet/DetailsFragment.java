package com.example.hp.offermagnet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    Button btnProposal, btnRate;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details, container, false);

        btnRate = (Button) v.findViewById(R.id.btnRate);
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment RateFragment = new RateFragment();
                FragmentManager ft = getFragmentManager();
                ft.beginTransaction().replace(R.id.OfferDetailLayout, RateFragment).commit();

            }
        });

        return v;
    }

}
