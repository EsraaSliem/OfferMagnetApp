package com.example.hp.offermagnet;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class Two extends Fragment  {


    public Two() {
        // Required empty public constructor
    }
    Database db;
    public static FloatingActionButton fab;
    ArrayList<DataItemRequest> dataItems=new ArrayList<DataItemRequest>();

    RecyclerRequestAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_request, container, false);
        final RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.recyclerviewRequest);
        db=new Database(getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://offer-system.000webhostapp.com/Discover.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String s = URLEncoder.encode(response,"ISO-8859-1");
                            response = URLDecoder.decode(s,"UTF-8");
                        }catch (UnsupportedEncodingException e){
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                          //  Toast.makeText(getActivity(),"response",Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                DataItemRequest item = new DataItemRequest(
                                        object.getString("request_id"),
                                        object.getString("title"),
                                        object.getString("description"),
                                        object.getString("profile_picture"),
                                        object.getString("validate_date"),
                                        object.getString("category_id"),
                                        object.getString("attachment"),
                                        object.getString("phone"),
                                        object.getString("user_name"),
                                        object.getString("user_id"),
                                        object.getString("city")
                                );
                                dataItems.add(item);
                            }
                            adapter = new RecyclerRequestAdapter(dataItems,getActivity());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("city","zagazig" );
                stringStringHashMap.put("age",String.valueOf(19));
                stringStringHashMap.put("gender",db.getGender());
                stringStringHashMap.put("cat1", "1");
                stringStringHashMap.put("cat2", "2");
                stringStringHashMap.put("cat3", "3");
                stringStringHashMap.put("cat4", "4");
                stringStringHashMap.put("cat5", "5");
                return stringStringHashMap;
            }

        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        fab = (FloatingActionButton) view.findViewById(R.id.fab_Request);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment productDetailFragment = new SubmitRequestFragment();
                FragmentManager ft=getFragmentManager();
                ft.beginTransaction().replace(R.id.frame_request, productDetailFragment).commit();
                fab.setVisibility(View.GONE);
            }
        });
        return view;
    }


   /* @Override
    public void onClick(View view, int position) {
        Fragment productDetailFragment = new RequestDetailsFragment();
        FragmentManager ft = getFragmentManager();
        ft.beginTransaction().replace(R.id.frame_request, productDetailFragment).commit();
        fab.setVisibility(View.GONE);
    }*/
}
