package com.example.hp.offermagnet;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ASUS on 21/03/2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter <ReviewsAdapter.ViewHolder> {
   private List<ListReviews> ListReview;
    private Context context;
    public ReviewsAdapter(List<ListReviews> listReview, Context context) {
        ListReview= listReview;
        this.context = context;
    }





    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext())
               .inflate(R.layout.list_reviews,parent,false);
       return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    ListReviews reviews= ListReview.get(position);
    holder.rate.setRating((float) reviews.getRate());
    holder.textName.setText(reviews.getName());



    }

    @Override
    public int getItemCount() {
        return ListReview.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    public RatingBar rate;
    public TextView textName;
        public ViewHolder(View itemView) {
            super(itemView);
            rate=(RatingBar) itemView.findViewById(R.id.rate);
            textName =(TextView)itemView.findViewById(R.id.name);
        }
    }

}
