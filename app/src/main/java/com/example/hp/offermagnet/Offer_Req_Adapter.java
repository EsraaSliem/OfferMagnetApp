package com.example.hp.offermagnet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class Offer_Req_Adapter extends RecyclerView.Adapter<Offer_Req_Adapter.ViewHolder> {

    private ArrayList<DataItemOfRequest> dataItem;
    Context context;
    Database db;
    View view;


    // data is passed into the constructor
    public  Offer_Req_Adapter(ArrayList<DataItemOfRequest> dataItem, Context context) {

        this.dataItem = dataItem;
        this.context = context;
        db=new Database(context);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_of_request,parent,false);
        //Toast.makeText(context,"Adapter",Toast.LENGTH_SHORT).show();
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder. desc .setText(dataItem.get(position).getOffer_Desc());
        holder.name.setText(dataItem.get(position).getUser_name());
        Picasso.with(context)
                .load(dataItem.get(position).getProfile_pic())
                .into(holder.offer_user_image);
         holder.accept.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://offer-system.000webhostapp.com/setAprrovedOffer.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String s = URLEncoder.encode(response, "ISO-8859-1");
                            response = URLDecoder.decode(s, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("request_id", dataItem.get(position).getId());

                stringStringHashMap.put("accepted", dataItem.get(position).getReq_id());

                return stringStringHashMap;
            }

        };

        Volley.newRequestQueue(context).add(stringRequest);

    }
});
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", dataItem.get(position).getPhone(), null)));
            }
        });






    }

    // total number of rows
    @Override
    public int getItemCount() {

        return dataItem.size();
    }




    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView offer_user_image;
        TextView desc,name;
        ImageButton accept;
        Button call;
        public ViewHolder(View itemView) {
            super(itemView);
            offer_user_image=itemView.findViewById(R.id.profile_img);
            desc =itemView.findViewById(R.id.txt_offer);
            name=itemView.findViewById(R.id.txt_name);
            accept =(ImageButton) itemView.findViewById(R.id.accept);
            call=(Button)itemView.findViewById(R.id.call);
            // btnDetails.setOnClickListener(this);
        }

        /*@Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
*/
    }


}