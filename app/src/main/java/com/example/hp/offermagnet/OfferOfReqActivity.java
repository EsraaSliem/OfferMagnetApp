package com.example.hp.offermagnet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OfferOfReqActivity extends AppCompatActivity {
RecyclerView recyclerView;
Database db;
    Offer_Req_Adapter adapter;
ArrayList<DataItemOfRequest> dataItems;
Bundle extras;
ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_of_req);
        extras=getIntent().getExtras();
        recyclerView = (RecyclerView) findViewById(R.id.OfferListRecyclerView);
        db=new Database(this);
        Toast.makeText(OfferOfReqActivity.this,extras.getString("Req_id"),Toast.LENGTH_SHORT).show();
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OfferOfReqActivity.this,NavDrawer.class);
                startActivity(intent);

            }
        });
        dataItems = new ArrayList<DataItemOfRequest>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading offer Data ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://offer-system.000webhostapp.com/getOffersOnRequestID.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();
                        try {
                            //              Toast.makeText(getActivity(),"entered to one ",Toast.LENGTH_SHORT).show();
                            String s = URLEncoder.encode(response,"ISO-8859-1");
                            response = URLDecoder.decode(s,"UTF-8");
                        }catch (UnsupportedEncodingException e){
                            e.printStackTrace();
                        }

                        progressDialog.dismiss();
                        try {
                            //            Toast.makeText(getActivity(),"try to one ",Toast.LENGTH_SHORT).show();
                            Log.i("tagconvertstr", "["+response+"]");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                //Toast.makeText(getActivity(),"entered to one tr ",Toast.LENGTH_SHORT).show();
                                DataItemOfRequest item = new DataItemOfRequest(

                                        object.getString("offer_id"),
                                        object.getString("offer_user"),
                                        object.getString("request_id"),
                                        object.getString("offer_desc"),
                                        object.getString("user_name"),
                                        object.getString("phone"),
                                        object.getString("profile_picture"),
                                        object.getString("req_user")

                                );
                                dataItems.add(item);
                            }
                            adapter = new Offer_Req_Adapter(dataItems,OfferOfReqActivity.this);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(OfferOfReqActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> stringStringHashMap = new HashMap<>();

                stringStringHashMap.put("request_id",extras.getString("Req_id"));
                stringStringHashMap.put("id",db.getId());

                return stringStringHashMap;
            }
        };

        Volley.newRequestQueue(OfferOfReqActivity.this).add(stringRequest);




    }
}
