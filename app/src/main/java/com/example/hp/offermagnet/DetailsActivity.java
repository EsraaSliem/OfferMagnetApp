package com.example.hp.offermagnet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsActivity extends AppCompatActivity {
    TextView txtDesc,DateFrom,DateTo,numberOfUsers;
    Button call,rate_user;
    ImageView share,like,img_product;
    CircleImageView img_User;
    RatingBar rateUser;
    String id;
    Database db;
ImageButton back;
    public  void onStart(){
        super.onStart();
        db=new Database(this);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetailsActivity.this,NavDrawer.class);
                startActivity(intent);

            }
        });
        img_User=findViewById(R.id.imageProfile_1);
        DateFrom=findViewById(R.id.txt_date_from);
        DateTo=findViewById(R.id.txt_date_finish);
        txtDesc=findViewById(R.id.pro_des_1);
        numberOfUsers=findViewById(R.id.numberOfUsers_1);
        rate_user=findViewById(R.id.btn_rate);
        rateUser=findViewById(R.id.rateUser_1);
        call=findViewById(R.id.btn_contact);
        share=findViewById(R.id.share_offer);
        like=findViewById(R.id.star);
        img_product=findViewById(R.id.pro_img_1);
        final Bundle extras= getIntent().getExtras();
        final String title=extras.getString("title");
        final String description=extras.getString("desc");
        String path=extras.getString("profile_picture");
        String path_product=extras.getString("product_image");
        final String from=extras.getString("from");
        final   String to=extras.getString("to");
        final String numberUser=extras.getString("likes");
        final String price=extras.getString("price");
        id=extras.getString("id");
        String rate=extras.getString("rate");
        String people=extras.getString("people");
        String txtNumberOfUser="( " + rate + " of 5 ) " + people + " Total";
        numberOfUsers.setText(txtNumberOfUser);
        DateTo.setText("Online Until " + to);
        DateFrom.setText("Started From " +from);
        txtDesc.setText("Title: " + title + "\nDescription\n" + description + "\nPrice: " + price);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailsActivity.this, "Start", Toast.LENGTH_SHORT).show();
                final ProgressDialog progressDialog = new ProgressDialog(DetailsActivity.this);
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
                                    like.setImageResource(R.drawable.star_shine);
                                    Toast.makeText(DetailsActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else if(response.contains("DisLike (Y)")) {
                                    like.setImageResource(R.drawable.star_garay);
                                    Toast.makeText(DetailsActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(DetailsActivity.this, response, Toast.LENGTH_SHORT).show();

                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(DetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> stringStringHashMap = new HashMap<>();
                        stringStringHashMap.put("user_id", db.getId());
                        stringStringHashMap.put("offer_id", id);
                        return stringStringHashMap;
                    }

                };
                Volley.newRequestQueue(DetailsActivity.this).add(stringRequest);
            }
        });




        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Title: " + title + "\nDescription\n" + description + "\nPrice: " + price+"\n"+
                        "Started From " + from+"\n"+"Online Until " + to;
                String Subject = "New Offer!";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, Subject);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                DetailsActivity.this.startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });

//rateUser.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        Picasso.with(this)
                .load(path)
                .into(img_User);
        numberOfUsers.setText(numberUser);

        Picasso.with(this)
                .load(path_product)
                .into(img_product);








        rate_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final float rate_ = rateUser.getRating();
                final ProgressDialog progressDialog = new ProgressDialog(DetailsActivity.this);
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

                                Toast.makeText(DetailsActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(DetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> stringStringHashMap = new HashMap<>();
                        stringStringHashMap.put("user_id", db.getId());
                        stringStringHashMap.put("offer_id",id);
                        stringStringHashMap.put("rate", rate_ + "");
                        return stringStringHashMap;
                    }

                };
                Volley.newRequestQueue(DetailsActivity.this).add(stringRequest);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsActivity.this.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", extras.getString("phone"), null)));
            }
        });


    }
}

