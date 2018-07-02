package com.example.hp.offermagnet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;





import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */


    public class GPSlocation extends AppCompatActivity {
    Database db;
    double MyLat,MyLong;
    String CityName;
    Button btn;
    String cityNmae;
    TextView txt;
    private Location location;
    private LocationListener locationListener;
    Context context;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gps);
                btn = (Button) findViewById(R.id.turnOnL);

            db=new Database(this);
            cityNmae=db.getCity();
            ActivityCompat.requestPermissions(GPSlocation.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getMyCurrentLocation();
                        if(MyLat==0.0&&MyLong==0.0){
                            Toast.makeText(GPSlocation.this, "Turn On LOcation", Toast.LENGTH_SHORT).show();
                        }
                        else {


                            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://offer-system.000webhostapp.com/GPSl.php",
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
                                            Intent intent = new Intent(GPSlocation.this, NavDrawer.class);
                                            startActivity(intent);

                                            if (response.contains("Done")) {
                                               // Intent intent = new Intent(GPSlocation.this, NavDrawer.class);

                                                //startActivity(intent);

                                                Toast.makeText(GPSlocation.this, response, Toast.LENGTH_SHORT).show();
                                            } else if (response.contains(" not Done")) {

                                                Toast.makeText(GPSlocation.this, response, Toast.LENGTH_SHORT).show();
                                            } else
                                                Toast.makeText(GPSlocation.this, response, Toast.LENGTH_SHORT).show();

                                        }

                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(GPSlocation.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                                    stringStringHashMap.put("user_id",db.getId() );
                                    stringStringHashMap.put("city", cityNmae);
                                    stringStringHashMap.put("longitude", String.valueOf(MyLong));
                                    stringStringHashMap.put("latitude", String.valueOf(MyLat));
                                    return stringStringHashMap;
                                }

                            };
                            Volley.newRequestQueue(GPSlocation.this).add(stringRequest);
                        }
                    }
                });




            }

            boolean gps_enabled,network_enabled;
            @SuppressLint("MissingPermission")
            void getMyCurrentLocation() {


                LocationManager locManager = (LocationManager)getSystemService( Context.LOCATION_SERVICE );
                LocationListener locListener = new MyLocationListener();


                try {
                    gps_enabled = locManager.isProviderEnabled( LocationManager.GPS_PROVIDER );
                } catch (Exception ex) {
                }
                try {
                    network_enabled = locManager.isProviderEnabled( LocationManager.NETWORK_PROVIDER );
                } catch (Exception ex) {
                }

                //don't start listeners if no provider is enabled
                //if(!gps_enabled && !network_enabled)
                //return false;

                if (gps_enabled) {
                    locManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, locListener );

                }


                if (gps_enabled) {
                    location = locManager.getLastKnownLocation( LocationManager.GPS_PROVIDER );


                }


                if (network_enabled && location == null) {
                    locManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 0, 0, locListener );

                }


                if (network_enabled && location == null) {
                    location = locManager.getLastKnownLocation( LocationManager.NETWORK_PROVIDER );

                }

                if (location != null) {

                    MyLat = location.getLatitude();
                    MyLong = location.getLongitude();


                } else {
                    Location loc = getLastKnownLocation( this );
                    if (loc != null) {


                        MyLat = loc.getLatitude();
                        MyLong = loc.getLongitude();


                    }
                }
                locManager.removeUpdates( locListener ); // removes the periodic updates from location listener to //avoid battery drainage. If you want to get location at the periodic intervals call this method using //pending intent.

                try {
// Getting address from found locations.
                    Geocoder geocoder;

                    List<Address> addresses;
                    geocoder = new Geocoder( this, Locale.getDefault() );
                    addresses = geocoder.getFromLocation( MyLat, MyLong, 1 );

//               StateName = addresses.get( 0 ).getAdminArea();
                    CityName = addresses.get( 0 ).getLocality();
                    //CountryName = addresses.get( 0 ).getCountryName();
                    // you can get more details other than this . like country code, state code, etc.


          /*  Toast.makeText(this," StateName " + StateName+"\n"+
                    " CityName " + CityName+"\n"+
                    " CountryName " + CountryName,Toast.LENGTH_LONG).show();
                    */
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Toast.makeText( this, "" + MyLat + "\n" + MyLong +""+CityName, Toast.LENGTH_LONG ).show();
                Intent intent = new Intent(GPSlocation.this, NavDrawer.class);
                startActivity(intent);

                Log.e( "Lat: ", MyLat + "" );
                Log.e( "Long: ", MyLong + "" );
            }

            // Location listener class. to get location.
            public class MyLocationListener implements LocationListener {
                public void onLocationChanged(Location location) {
                    if (location != null) {
                    }
                }

                public void onProviderDisabled(String provider) {
                    Intent b = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(b);

                }

                public void onProviderEnabled(String provider) {
                    // TODO Auto-generated method stub
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                    // TODO Auto-generated method stub
                }
            }

// below method to get the last remembered location. because we don't get locations all the times .At some instances we are unable to get the location from GPS. so at that moment it will show us the last stored location.

            public static Location getLastKnownLocation(Context context) {
                Location location = null;
                @SuppressLint("WrongConstant") LocationManager locationmanager = (LocationManager) context.getSystemService( "location" );
                List list = locationmanager.getAllProviders();
                boolean i = false;
                Iterator iterator = list.iterator();
                do {
                    //System.out.println("---------------------------------------------------------------------");
                    if (!iterator.hasNext())
                        break;
                    String s = (String) iterator.next();
                    //if(i != 0 && !locationmanager.isProviderEnabled(s))
                    if (i != false && !locationmanager.isProviderEnabled( s ))
                        continue;
                    // System.out.println("provider ===> "+s);
                    @SuppressLint("MissingPermission") Location location1 = locationmanager.getLastKnownLocation( s );
                    if (location1 == null)

                        continue;
                    if (location != null) {
                        //System.out.println("location ===> "+location);
                        //System.out.println("location1 ===> "+location);
                        float f = location.getAccuracy();
                        float f1 = location1.getAccuracy();
                        if (f >= f1) {
                            long l = location1.getTime();
                            long l1 = location.getTime();
                            if (l - l1 <= 600000L)
                                continue;
                        }
                    }
                    location = location1;
                    // System.out.println("location  out ===> "+location);
                    //System.out.println("location1 out===> "+location);
                    i = locationmanager.isProviderEnabled( s );
                    // System.out.println("---------------------------------------------------------------------");
                } while (true);
                return location;
            }


        }
