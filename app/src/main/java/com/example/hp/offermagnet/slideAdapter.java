package com.example.hp.offermagnet;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class slideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public slideAdapter(Context context){
        this.context=context;
    }
    public int [] slide_image={
            R.drawable.splash1,
            R.drawable.splash2,
            R.drawable.splash3,
            R.drawable.splash4

    };
    public String [] slide_desc={
            "Save your saving rise by finding Different prices",
            "Never miss out on an offer ever",
            "find the product or services in the same region",
            "User or group of users initiate a request for certain product"
    };
    @Override
    public int getCount() {
        return slide_image.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==(RelativeLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide,container,false);
        ImageView slideImageView=(ImageView) view.findViewById(R.id.slideImage);
        TextView textView=(TextView) view.findViewById(R.id.slideText);
        slideImageView.setImageResource(slide_image[position]);
        textView.setText(slide_desc[position]);

container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
