package com.example.hp.offermagnet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class RequestDetailsActivity extends AppCompatActivity {
    Button btnJoin;
    ImageView img_request;
    CircleImageView img_user;
    TextView usrName,desc,to;
    Database db;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        final Bundle extras= getIntent().getExtras();
        db=new Database(this);
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RequestDetailsActivity.this,NavDrawer.class);
                startActivity(intent);

            }
        });
        final String title=extras.getString("title");
        final String description=extras.getString("desc");
        String path=extras.getString("profile_picture");
        String path_attachment=extras.getString("attachment");
        String name=extras.getString("user_name");

        final String validate_date=extras.getString("validate_date");
        final String id=extras.getString("id");


        img_request =findViewById(R.id.img_Product);
        img_user =findViewById(R.id.imageProfile_request);

        usrName =findViewById(R.id.user_name_request);
        desc=findViewById(R.id.txt_Desc_request);
        to=findViewById(R.id.txt_To_request);

        to.setText(validate_date);
        usrName.setText(name);
        Picasso.with(this)
                .load(path)
                .into(img_user);


        Picasso.with(this)
                .load(path_attachment)
                .into(img_request);
        desc.setText("Title: " + title + "\nDescription\n" + description );


        btnJoin=findViewById(R.id.join_Request)    ;


        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RequestDetailsActivity.this, "Start", Toast.LENGTH_SHORT).show();
                final ProgressDialog progressDialog = new ProgressDialog(RequestDetailsActivity.this);
                progressDialog.setMessage("Loading ...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://offer-system.000webhostapp.com/Join.php",
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
                                Log.i("tagconvertstr", "["+response+"]");
                                Toast.makeText(RequestDetailsActivity.this, response, Toast.LENGTH_SHORT).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(RequestDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> stringStringHashMap = new HashMap<>();
                        stringStringHashMap.put("user_id", db.getId());
                        stringStringHashMap.put("request_id",id);
                        return stringStringHashMap;
                    }

                };
                Volley.newRequestQueue(RequestDetailsActivity.this).add(stringRequest);
            }
        });



    }



}
