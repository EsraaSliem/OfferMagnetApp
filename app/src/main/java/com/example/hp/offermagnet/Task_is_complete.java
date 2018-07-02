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


public class Task_is_complete extends Fragment {

    Button btnHome;

    public Task_is_complete() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_is_complete, container, false);
        btnHome=(Button)view.findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment requests = new Two();
                FragmentManager ft = getFragmentManager();
                ft.beginTransaction().replace(R.id.frameDoneRequest, requests).commit();
            }
        });




        return view;
    }


}
