package com.example.hp.offermagnet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hp on 25/02/2018.
 */

public class LogIn extends Fragment {
    Button btn;
    EditText phone;
    EditText pass;
    String var;
    boolean phonev, passv;
    Database database;
    SharedPreferences.Editor prefEditor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_signin, container, false);
        phone = (EditText) rootView.findViewById(R.id.phoneEditText);
        pass = (EditText) rootView.findViewById(R.id.passEditText);
        prefEditor= getActivity().getSharedPreferences("AppPrefrences", Context.MODE_PRIVATE).edit();
        database = new Database(getActivity());
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (phone.getText().length() <= 10) {
                    phone.setError("Required at least 11 number");
                    phonev = false;
                } else
                    phonev = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pass.getText().length() <= 6) {
                    pass.setError("Required at least 7 digits");
                    passv = false;
                } else
                    passv = true;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn = (Button) rootView.findViewById(R.id.btnlogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phonev && passv) {
                    login();



                }
                else{
                    Toast.makeText(getContext().getApplicationContext(), "yor pass or phone is error", Toast.LENGTH_LONG).show();
                }

            }
        });
        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();


    }

    private void login() {


        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://offer-system.000webhostapp.com/Login.php",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         Toast.makeText(getContext().getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("response");
                            Toast.makeText(getActivity(), response,Toast.LENGTH_SHORT).show();
                            for (int x = 0; x < jsonArray.length(); x++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(x);
                                if (jsonObject1.getString("state").equals("OK")) {
                                    database.UpdateData("1",jsonObject1.getString("id"),
                                            jsonObject1.getString("user_name"),
                                            jsonObject1.getString("phone"),
                                            jsonObject1.getString("password"),
                                            jsonObject1.getString("city"),
                                            jsonObject1.getString("gender"),
                                            "1",
                                            jsonObject1.getString("birthdate"),
                                            jsonObject1.getString("profile_picture")

                                            );

                                    Intent intent = new Intent(getContext(), GPSlocation.class);
                                    prefEditor.putBoolean("logined",true);
                                    prefEditor.apply();
                                    startActivity(intent);

                                } else if (jsonObject1.getString("state").equals("NO"))

                                    Toast.makeText(getContext().getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof ServerError)
                    Toast.makeText(getContext().getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(getContext().getApplicationContext(), "Connection Timed Out", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(getContext().getApplicationContext(), "Bad Network Connection", Toast.LENGTH_SHORT).show();
            }


        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("phone", phone.getText().toString());
                param.put("password", pass.getText().toString());
                return param;

            }


        };
        Volley.newRequestQueue(getContext()).add(postRequest);

    }
}