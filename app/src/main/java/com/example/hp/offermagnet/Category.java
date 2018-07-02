package com.example.hp.offermagnet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class Category extends AppCompatActivity {

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btncontinue;
    Bundle extras;

    final HashMap<String, String> category = new HashMap<String, String>();

    boolean check[] = {false, false, false, false, false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        extras= getIntent().getExtras();
        btn1 = (Button) findViewById(R.id.foods);
        btn2 = (Button) findViewById(R.id.books);
        btn3 = (Button) findViewById(R.id.fashions);
        btn5 = (Button) findViewById(R.id.electronics);
        btn6 = (Button) findViewById(R.id.collections);
        btn7 = (Button) findViewById(R.id.shoses);
        btn8 = (Button) findViewById(R.id.cellphone);
        btn9 = (Button) findViewById(R.id.computer);
        btn4 = (Button) findViewById(R.id.officies);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check[0] = !check[0];
                if (check[0] == true) {
                    btn1.setBackgroundColor(Color.rgb(81, 171, 255));
                    category.put("cat1", "1");
                } else {
                    btn1.setBackgroundColor(Color.rgb(249, 251, 255));
                }

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check[1] = !check[1];
                if (check[1] == true) {
                    btn2.setBackgroundColor(Color.rgb(81, 171, 255));
                    category.put("cat2", "2");
                } else {
                    btn2.setBackgroundColor(Color.rgb(249, 251, 255));
                }

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check[2] = !check[2];
                if (check[2] == true) {
                    btn3.setBackgroundColor(Color.rgb(81, 171, 255));
                    category.put("cat3", "3");
                } else {
                    btn3.setBackgroundColor(Color.rgb(249, 251, 255));
                }

            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check[3] = !check[3];
                if (check[3] == true) {
                    btn4.setBackgroundColor(Color.rgb(81, 171, 255));
                    category.put("cat4", "4");
                } else {
                    btn4.setBackgroundColor(Color.rgb(249, 251, 255));
                }


            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check[4] = !check[4];
                if (check[4] == true) {
                    btn5.setBackgroundColor(Color.rgb(81, 171, 255));
                    category.put("cat5", "5");
                } else {
                    btn5.setBackgroundColor(Color.rgb(249, 251, 255));
                }

            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check[5] = !check[5];
                if (check[5] == true) {
                    btn6.setBackgroundColor(Color.rgb(81, 171, 255));
                    // category.put("cat6","collections");
                } else {
                    btn6.setBackgroundColor(Color.rgb(249, 251, 255));
                }

            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check[6] = !check[6];
                if (check[6] == true) {
                    btn7.setBackgroundColor(Color.rgb(81, 171, 255));
                    category.put("cat3", "3");
                } else {
                    btn7.setBackgroundColor(Color.rgb(249, 251, 255));
                }

            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check[7] = !check[7];
                if (check[7] == true) {
                    btn8.setBackgroundColor(Color.rgb(81, 171, 255));
                    category.put("cat5", "5");
                } else {
                    btn8.setBackgroundColor(Color.rgb(249, 251, 255));
                }

            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check[8] = !check[8];
                if (check[8] == true) {
                    btn9.setBackgroundColor(Color.rgb(81, 171, 255));
                    category.put("cat5", "5");
                } else {
                    btn9.setBackgroundColor(Color.rgb(249, 251, 255));
                }

            }
        });


        btncontinue = (Button) findViewById(R.id.btncontinue);
        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCategory();

            }

        });
    }

    void selectCategory() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://offer-system.000webhostapp.com/Category.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.contains("No parameters ! ")) {
                           // Toast.makeText(Category.this, "connect", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Category.this, UserTab.class);
                            startActivity(intent);
                        } else

                            Toast.makeText(Category.this, "error", Toast.LENGTH_LONG).show();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof ServerError)
                    Toast.makeText(Category.this, "Server Error", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(Category.this, "Connection Timed Out", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(Category.this, "Bad Network Connection", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> select_category = new HashMap<String, String>();
                select_category = category;
                select_category.put("user_id", extras.getString("user_id"));
                return select_category;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);


    }
}
