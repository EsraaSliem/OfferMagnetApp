package com.example.hp.offermagnet;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class RequestDetailsFragment extends Fragment {

    public RequestDetailsFragment() {
        // Required empty public constructor
    }


Button addOffer,join;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_request_details, container, false);
        addOffer=(Button)v.findViewById(R.id.addOffer);
        join=(Button)v.findViewById(R.id.joinR);
        addOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment add = new AddOfferToRequestFragment();
                FragmentManager ft = getFragmentManager();
                ft.beginTransaction().replace(R.id.requestDetails, add).commit();
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment productDetailFragment = new Two();
                FragmentManager ft = getFragmentManager();
                ft.beginTransaction().replace(R.id.requestDetails, productDetailFragment).commit();

            }
        });
        return v;
    }



}
