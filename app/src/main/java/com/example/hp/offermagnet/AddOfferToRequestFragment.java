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
import android.widget.Toast;


public class AddOfferToRequestFragment extends Fragment {
  Button send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_add_offer_to_request, container, false);
        send=(Button)v.findViewById(R.id.btnSendOffer);
         send.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(getContext().getApplicationContext(), "your offer is sent", Toast.LENGTH_LONG).show();
                 Fragment fragment = new Two();
                 FragmentManager ft = getFragmentManager();
                 ft.beginTransaction().replace(R.id.addOffertorequestFrameLayout, fragment).commit();
             }
         });
        return v;
    }

}
