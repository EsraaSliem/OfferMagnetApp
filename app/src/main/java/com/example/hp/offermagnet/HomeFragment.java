package com.example.hp.offermagnet;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    String srchTxt="";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        if(getArguments().getString("srchTxt").equals("")) {

        }else{
           srchTxt=getArguments().getString("srchTxt");
        }


        TabLayout tabs = (TabLayout) view.findViewById(R.id.tabs2);
        ViewPager pager = (ViewPager) view.findViewById(R.id.viewpager2);
        PagerAdapter TabAdapter = new tabpagerAdapter(getActivity().getSupportFragmentManager(),srchTxt);
        pager.setAdapter(TabAdapter);

        tabs.setupWithViewPager(pager);




        return view;
    }

}
