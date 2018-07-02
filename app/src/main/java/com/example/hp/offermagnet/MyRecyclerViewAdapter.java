package com.example.hp.offermagnet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ArrayList<DataItem> dataItem;
    Context context;
    private ItemClickListener clickListener;
    LayoutInflater inflater;
    View alertLayout;
    RatingBar rateUser;
    TextView numberOfUsers, txtPath, txtfinish, pro_des;
    ImageView pro_img;
    CircleImageView imageProfile;
    Button btncontact, btnrate;
    ImageView star_garay, share;
    View view;
    Database db;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(ArrayList<DataItem> dataItem, Context context) {

        this.dataItem = dataItem;
        this.context = context;
        db=new Database(context);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        //Toast.makeText(context,"Adapter",Toast.LENGTH_SHORT).show();
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        alertLayout = inflater.inflate(R.layout.fragment_details, null);

        rateUser = alertLayout.findViewById(R.id.rateUser);
        numberOfUsers = alertLayout.findViewById(R.id.numberOfUsers);
        imageProfile = alertLayout.findViewById(R.id.imageProfile);
        txtPath = alertLayout.findViewById(R.id.txtPath);
        share = alertLayout.findViewById(R.id.share);
        star_garay = alertLayout.findViewById(R.id.star_garay);
        btncontact = alertLayout.findViewById(R.id.btncontact);
        btnrate = alertLayout.findViewById(R.id.btnRate);
        txtfinish = alertLayout.findViewById(R.id.txtfinish);
        pro_img = alertLayout.findViewById(R.id.pro_img);
        pro_des = alertLayout.findViewById(R.id.pro_des);

        Picasso.with(context)
                .load(dataItem.get(position).getImageUrl())
                .into(holder.usrImage);
        //Toast.makeText(context,dataItem.get(position).getImageUrl(),Toast.LENGTH_SHORT).show();

        holder.txtTitle.setText(dataItem.get(position).getTitle());
        holder.desc.setText(dataItem.get(position).getDesc());
        //holder.usrImage.setImageResource(R.drawable.star_shine);
        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                // Show Details Action

                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Details");
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (view != null) {
                            ViewGroup parent = (ViewGroup) view.getParent();
                            if (parent != null) {
                                parent.removeAllViews();
                            }
                        }
                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();

                Picasso.with(context)
                        .load(dataItem.get(position).getImageUrl())
                        .into(imageProfile);
                if (!dataItem.get(position).getProductImageUrl() .isEmpty())
                    Picasso.with(context)
                            .load(dataItem.get(position).getProductImageUrl())
                            .into(pro_img);

                btncontact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", dataItem.get(position).getPhone(), null)));
                    }
                });

                btnrate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final float rate = rateUser.getRating();
                        final ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Rating ...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://offer-system.000webhostapp.com/RateOffer.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            String s = URLEncoder.encode(response, "ISO-8859-1");
                                            response = URLDecoder.decode(s, "UTF-8");
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                        progressDialog.dismiss();
                                       // Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                              //  Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> stringStringHashMap = new HashMap<>();
                                stringStringHashMap.put("user_id", db.getId());
                                stringStringHashMap.put("offer_id", dataItem.get(position).getId());
                                stringStringHashMap.put("rate", rate + "");
                                return stringStringHashMap;
                            }

                        };
                        Volley.newRequestQueue(context).add(stringRequest);
                    }
                });
                rateUser.setBackgroundColor(R.color.colorWhite);
                numberOfUsers.setText("( " + dataItem.get(position).getRate() + " of 5 ) " + dataItem.get(position).getPeople() + " Reviews");
                txtfinish.setText("Online Until " + dataItem.get(position).getDateTo());
                txtPath.setText("Started From " + dataItem.get(position).getDateFrom());
                pro_des.setText("Title: " + dataItem.get(position).getTitle() + "\nDescription\n" + dataItem.get(position).getDesc() + "\nPrice: " + dataItem.get(position).getPrice());
                star_garay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  Toast.makeText(context, "Start", Toast.LENGTH_SHORT).show();
                        final ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Loading ...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://offer-system.000webhostapp.com/InLike.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            String s = URLEncoder.encode(response, "ISO-8859-1");
                                            response = URLDecoder.decode(s, "UTF-8");
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        }
                                        progressDialog.dismiss();
                                        if(response.contains("Like (Y)")){
                                            star_garay.setImageResource(R.drawable.star_shine);
                                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                        }
                                        else if(response.contains("DisLike (Y)")) {
                                            star_garay.setImageResource(R.drawable.star_garay);
                                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();

                                    }

                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> stringStringHashMap = new HashMap<>();
                                stringStringHashMap.put("user_id", db.getId());
                                stringStringHashMap.put("offer_id", dataItem.get(position).getId());
                                return stringStringHashMap;
                            }

                        };
                        Volley.newRequestQueue(context).add(stringRequest);
                    }
                });

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Contact person :"+dataItem.get(position).user_name+"\nTitle: " + dataItem.get(position).getTitle() + "\nDescription\n" + dataItem.get(position).getDesc() + "\nPrice: " + dataItem.get(position).getPrice()+"\n"+
                        "Started From " + dataItem.get(position).getDateFrom()+"\n"+"Online Until " + dataItem.get(position).getDateTo();
                String Subject = "New Offer!";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, Subject);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {

        return dataItem.size();
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, desc;
        CircleImageView usrImage;
        Button btnDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            desc = (TextView) itemView.findViewById(R.id.txtContent);
            usrImage = (CircleImageView) itemView.findViewById(R.id.userPhoto);
            btnDetails = (Button) itemView.findViewById(R.id.detailsButton);
            // btnDetails.setOnClickListener(this);
        }

        /*@Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
*/
    }


}