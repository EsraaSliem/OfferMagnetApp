package com.example.hp.offermagnet;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

Database db;
    public ProfileFragment() {
        // Required empty public constructor
    }
    String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Button edit;
        final EditText name,phone,pass;
        CircleImageView image;
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        db=new Database(getActivity());
        name=(EditText)view.findViewById(R.id.usr_name);
        name.setText(db.getName());

        name.setEnabled(false);
        phone=(EditText)view.findViewById(R.id.usr_phone);
        phone.setText(db.getPhone());
        phone.setEnabled(false);
        pass=(EditText)view.findViewById(R.id.usr_pass);
        pass.setText("********");
        pass.setEnabled(false);
        image=(CircleImageView)view.findViewById(R.id.image);
        Picasso.with(getActivity())
                .load(db.getImage())
                .into(image);
        edit=(Button) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"presed",Toast.LENGTH_SHORT).show();

                if(edit.getText().equals("edit")){
                    name.setEnabled(true);
                    phone.setEnabled(true);
                    pass.setEnabled(true);
                    edit.setText("Save");

                }
                else{
                    if(pass.getText().toString().equals("********")){
                        password=db.getPass();
                    }
                    else {
                        password=pass.getText().toString();
                    }
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://offer-system.000webhostapp.com/UpdateInfo.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        String s = URLEncoder.encode(response, "ISO-8859-1");
                                        response = URLDecoder.decode(s, "UTF-8");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    Log.i("tagconvertstr", "[" + response + "]");
                                    if (response.contains("Done")) {
                                        Intent intent = new Intent(getContext(), NavDrawer.class);

                                        startActivity(intent);

                                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                    } else if (response.contains(" not Done")) {

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
                            stringStringHashMap.put("user_id",db.getId() );
                            stringStringHashMap.put("user_name", name.getText().toString());
                            stringStringHashMap.put("password", password);
                            stringStringHashMap.put("phone", phone.getText().toString());
                            return stringStringHashMap;
                        }

                    };
                    Volley.newRequestQueue(getContext()).add(stringRequest);

                }
            }
        });
        return view;
    }

}
