package com.example.hp.offermagnet;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.WindowManager;
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

import java.util.HashMap;
import java.util.Map;

public class AlertService extends Service {

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;

    String id,city;

//DatabaseForNotify dbForNotify;
    public AlertService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
       // dbForNotify=new DatabaseForNotify(this);
      // Cursor cursor=dbForNotify.ShowData();
     //  id=cursor.getString(1);
      // city=cursor.getString(2);

        Toast.makeText(this, "Service Starts!", Toast.LENGTH_LONG).show();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                Toast.makeText(context, "Service is stIll running", Toast.LENGTH_LONG).show();
                handler.postDelayed(runnable,300000 /*5*60*10000*/); // 5 Minuit

                StringRequest stringRequest = new StringRequest( Request.Method.POST, "https://offer-system.000webhostapp.com/GetOffersnotification.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("response");
                                for (int x = 0; x < jsonArray.length(); x++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(x);
                                    jsonObject1.getString("id");
                                    jsonObject1.getString("title");
                                    jsonObject1.getString("description");
                                    Toast.makeText(context, jsonObject1.getString("id"), Toast.LENGTH_LONG).show();

                                }


                                }catch (JSONException e) {
                                e.printStackTrace();
                            }

                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap hashMap = new HashMap();
                        hashMap.put("id",id);
                        hashMap.put("city",city);
                        return hashMap;
                    }
                };
                Volley.newRequestQueue(context).add(stringRequest);
            }
        };
        handler.postDelayed(runnable, 15000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(context, "On Start Command", Toast.LENGTH_LONG).show();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
        Toast.makeText(this, "Ride Finished", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Ride Started By Customer.", Toast.LENGTH_LONG).show();
    }
}
