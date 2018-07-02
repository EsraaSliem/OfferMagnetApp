package com.example.hp.offermagnet;

import android.app.ProgressDialog;
import android.content.Intent;
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
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class One extends Fragment  {
    public static FloatingActionButton fab;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    ArrayList<DataItem> dataItems;
Database db;
String srchTxt;
boolean load=true;
String serachText;
    public One() {
        // Required empty public constructor
    }
public  void onStart() {

    super.onStart();
    //db=new Database(getActivity());

}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_offer, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewOffer);

        dataItems = new ArrayList<DataItem>();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        db=new Database(getActivity());
//        Toast.makeText(getActivity(),"city is: "+db.getCity(),Toast.LENGTH_SHORT).show();

        if(getArguments().getString("srchTxt").equals("")) {
            getAllOfers();
        }else{
            serachText= getArguments().getString("srchTxt");
     //       Toast.makeText(getActivity(),"one:::"+serachText,Toast.LENGTH_SHORT).show();
            setSeacrh(srchTxt);
        }





        //recyclerView.setAdapter(adapter);
        //adapter.setClickListener(this);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        fab = (FloatingActionButton) view.findViewById(R.id.fab_Offer);
        final boolean[] check = {false};
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check[0] = !check[0];
                if (check[0] == true) {
                    Fragment productDetailFragment = new SubmitOfferFragment();
                    FragmentManager ft = getFragmentManager();
                        ft.beginTransaction().replace(R.id.frame_offer, productDetailFragment).addToBackStack( "newoffer" ).commit();
                    fab.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

   /* @Override
    public void onClick(View view, int position) {
        Fragment productDetailFragment = new DetailsFragment();
        FragmentManager ft = getFragmentManager();
        ft.beginTransaction().replace(R.id.frame_offer, productDetailFragment).commit();
        fab.setVisibility(View.GONE);

    }*/

   public void getAllOfers(){
       final ProgressDialog progressDialog = new ProgressDialog(getActivity());
       progressDialog.setMessage("Loading offer Data ...");
       progressDialog.show();
       StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://offer-system.000webhostapp.com/GetOffers.php",
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       //Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();
                       try {
                           //              Toast.makeText(getActivity(),"entered to one ",Toast.LENGTH_SHORT).show();
                           String s = URLEncoder.encode(response, "ISO-8859-1");
                           response = URLDecoder.decode(s, "UTF-8");
                       } catch (UnsupportedEncodingException e) {
                           e.printStackTrace();
                       }

                       progressDialog.dismiss();
                       try {
                           //            Toast.makeText(getActivity(),"try to one ",Toast.LENGTH_SHORT).show();
                           Log.i("tagconvertstr", "[" + response + "]");
                           JSONObject jsonObject = new JSONObject(response);
                           JSONArray jsonArray = jsonObject.getJSONArray("data");
                           for (int i = 0; i < jsonArray.length(); i++) {
                               JSONObject object = jsonArray.getJSONObject(i);
                               //Toast.makeText(getActivity(),"entered to one tr ",Toast.LENGTH_SHORT).show();
                               DataItem item = new DataItem(

                                       object.getString("id"),
                                       object.getString("title"),
                                       object.getString("description"),
                                       object.getString("profile_picture"),
                                       object.getString("date_from"),
                                       object.getString("date_to"),
                                       object.getString("price"),
                                       object.getString("likes"),
                                       object.getInt("rate"),
                                       object.getString("product_image"),
                                       object.getString("phone"),
                                       object.getString("people"),
                                       object.getString("user_name")
                               );
                               dataItems.add(item);
                           }
                           adapter = new MyRecyclerViewAdapter(dataItems, getActivity());
                           recyclerView.setAdapter(adapter);

                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               progressDialog.dismiss();
               Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
           }
       }) {
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               HashMap<String, String> stringStringHashMap = new HashMap<>();
               stringStringHashMap.put("city", db.getCity());
               return stringStringHashMap;
           }
       };

       Volley.newRequestQueue(getActivity()).add(stringRequest);
   }


   public void setSeacrh(String text){
       srchTxt=text;
       load=false;
       StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://offer-system.000webhostapp.com/SearchOffer.php",
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       try {
                           load=true;
                           //              Toast.makeText(getActivity(),"entered to one ",Toast.LENGTH_SHORT).show();
                           String s = URLEncoder.encode(response,"ISO-8859-1");
                           response = URLDecoder.decode(s,"UTF-8");
                       }catch (UnsupportedEncodingException e){
                           e.printStackTrace();
                       }


                       try {
                           //            Toast.makeText(getActivity(),"try to one ",Toast.LENGTH_SHORT).show();
                           Log.i("tagconvertstr", "["+response+"]");
                           JSONObject jsonObject = new JSONObject(response);
                           JSONArray jsonArray = jsonObject.getJSONArray("data");
                           for (int i=0; i<jsonArray.length(); i++){
                               JSONObject object = jsonArray.getJSONObject(i);
                               //Toast.makeText(getActivity(),"entered to one tr ",Toast.LENGTH_SHORT).show();
                               DataItem item = new DataItem(

                                       object.getString("id"),
                                       object.getString("title"),
                                       object.getString("description"),
                                       object.getString("profile_picture"),
                                       object.getString("date_from"),
                                       object.getString("date_to"),
                                       object.getString("price"),
                                       object.getString("likes"),
                                       object.getInt("rate"),
                                       object.getString("product_image"),
                                       object.getString("phone"),
                                       object.getString("people")
                               );

                               dataItems.add(item);
                           }
                           adapter = new MyRecyclerViewAdapter(dataItems,getActivity());
                           adapter.notifyDataSetChanged();
                           recyclerView.setAdapter(adapter);

                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                       Log.i("tagconvertstr", "[" + response + "]");

                        if (response.contains(" not Done")) {

                           Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                       } else
                           Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                   }

               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

               Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
           }
       }) {
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               HashMap<String, String> stringStringHashMap = new HashMap<>();
               stringStringHashMap.put("city",db.getCity() );
               stringStringHashMap.put("word", "and");

               return stringStringHashMap;
           }

       };
       Volley.newRequestQueue(getContext()).add(stringRequest);

    }

}
