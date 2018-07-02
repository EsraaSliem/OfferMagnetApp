package com.example.hp.offermagnet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FQFragment extends Fragment {


    public FQFragment() {
        // Required empty public constructor
    }

TextView txt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_fq, container, false);
        txt=(TextView)v.findViewById(R.id.terms);
        txt.setMovementMethod(new ScrollingMovementMethod());
        return v;
    }

}
