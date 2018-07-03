package com.example.hp.offermagnet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SignUp  extends Fragment  {
    TextView txtDate;
    Button btn,btnSetDate;
    EditText pass ,fname,cpass,phone;;
    Spinner spnGinder, Capital;

    double a=0.0;
    boolean vn=false;
    boolean vphone=false;

    boolean pc=false;
    boolean vpc=false;
    int age;
    private DatePickerDialog.OnDateSetListener DatePicker1;
    User user=new User();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.tab_signup, container, false);
        final Calendar today=Calendar.getInstance();
        pass=(EditText)rootView.findViewById(R.id.password);
        cpass=(EditText)rootView.findViewById(R.id.cpassword);
        phone=(EditText) rootView.findViewById(R.id.phone);
        fname=(EditText) rootView.findViewById(R.id.firstNme);
        spnGinder =(Spinner)rootView.findViewById(R.id.gender);
        btnSetDate=(Button)rootView.findViewById(R.id.btnSetDateOfBirth);
        txtDate =(TextView) rootView.findViewById(R.id.textDateOfBirth);
        Capital=(Spinner)rootView.findViewById(R.id.Capital);

        fname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(fname.getText().length()<=0)
                {
                    fname.setError("please enter a valid name");
                }
                else {
                    vn=true;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(phone.getText().length()==0)
                {
                    phone.setError("Required");
                }
                else if(phone.getText().length()<=10){
                    phone.setError("Required at least 11 number");
                }
                else if(!phone.getText().toString().substring(0,3).equals("010")&&!phone.getText().toString().substring(0,3).equals("011")
                        &&!phone.getText().toString().substring(0,3).equals("012")&&!phone.getText().toString().substring(0,3).equals("015")){
                    phone.setError("invalid phone number");
                }
                else {
                    vphone=true;

                }

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

                if(pass.getText().length()==0)
                {
                    pass.setError("Required");
                }
                else if(pass.getText().length()<8){
                    pass.setError("reqiured at least 8 character");
                }
                else
                {
                    pc=true;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(cpass.getText().length()==0)
                {
                    cpass.setError("Required");
                }

                else if(! cpass.getText().toString().equals(pass.getText().toString()))
                {
                    cpass.setError("missmatch password");
                }
                else {
                    vpc=true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        final int yearx=today.get(Calendar.YEAR);
        int month= today.get(Calendar.MONTH)+1;
        int year =today.get(Calendar.YEAR);
        ;
        btnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();


                final  int yearU=cal.get(Calendar.YEAR);
                int monthU=cal.get(Calendar.MONTH);
                int dayU=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog=new DatePickerDialog(
                        rootView.getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , DatePicker1
                        ,yearU,monthU,dayU);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }

        });
        DatePicker1=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date=month+"/" + dayOfMonth +"/"+year;
                txtDate.setText(date);
                age=yearx-year;
            }
        };

        ArrayAdapter<CharSequence> dataAdapter =ArrayAdapter.createFromResource(rootView.getContext(),R.array.gender,android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGinder.setAdapter(dataAdapter);

        btn=(Button) rootView.findViewById(R.id.signup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(getContext(), ""+vn+""+pc+""+vphone+""+spnGinder.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                if(vn&&pc&&vpc&&vphone&&!spnGinder.getSelectedItem().toString().equals("gender")&&!Capital.getSelectedItem().toString().equals("city"))
                    if(age>18){
                        trySignUp();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "you can't register you are under age"+age, Toast.LENGTH_LONG).show();
                    }
                else
                    Toast.makeText(getContext(), "please fill info"+Capital.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

            }
        });



        return rootView;
    }

    protected  void trySignUp(){

        StringRequest postRequest = new StringRequest(Request.Method.POST,"https://offer-system.000webhostapp.com/Register.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     //   Toast.makeText(getContext().getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            if( jsonObject.getString("id").equals("phone already Exist!"))
                                Toast.makeText(getContext().getApplicationContext(), "Enter another phone", Toast.LENGTH_LONG).show();
                            else if (!jsonObject.getString("id").equals("NULL") ) {


                                Toast.makeText(getContext().getApplicationContext(), "connect", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(getContext(), uploadImage.class);
                                intent.putExtra("user_id",jsonObject.getString("id") );
                                startActivity(intent);
//                            user.setId(Integer.parseInt(jsonObject.getString("id"))+"");
//                            contact contacts=new contact(user.getId(),fname.getText().toString(),phone.getText().toString());
//                            final DatabaseHandler db=new DatabaseHandler(getContext());
//                            Intent intent = new Intent(getContext(), Category.class);
//                            db.insert(contacts);
//
//                            startActivity(intent);

                            } else

                                Toast.makeText(getContext().getApplicationContext(), "error", Toast.LENGTH_LONG).show();

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
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map <String,String> param=new HashMap<String, String>() ;
                param.put("name",fname.getText().toString());
                param.put("user_phone",phone.getText().toString());
                param.put("password",pass.getText().toString());
                param.put("age", String.valueOf(age));
                param.put("gender",spnGinder.getSelectedItem().toString());
                param.put("lon",String.valueOf(a));
                param.put("lat",String.valueOf(a));
                param.put("captial",Capital.getSelectedItem().toString());

                return  param;

            }


        };
        Volley.newRequestQueue(getContext()).add(postRequest);

    }




}
