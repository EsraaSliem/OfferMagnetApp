package com.example.hp.offermagnet;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment {
    MyorderAdapter adapter;

    public MyOrderFragment() {
        // Required empty public constructor
    }
    Database db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_order, container, false);
        db=new Database(getActivity());

        final RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.recyclerviewMyOrder);

        final ArrayList<DataItemRequest> dataItems=new ArrayList<DataItemRequest>();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Data ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://offer-system.000webhostapp.com/MyOrders.php",
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
                        Log.i("tagconvertstr", "["+response+"]");
                        try {

                            JSONObject jsonObject = new JSONObject(response);

//                            Toast.makeText(getActivity(),"response",Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                          //      Toast.makeText(getActivity(),"converted",Toast.LENGTH_SHORT).show();
                                DataItemRequest item = new DataItemRequest(
                                        object.getString("id"),
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
                            adapter = new MyorderAdapter(dataItems,getActivity());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
              //  Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("user_id",db.getId());

                return stringStringHashMap;
            }

        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);

        return view;
    }

}
