package com.example.hp.offermagnet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class Location_Fragment extends Fragment {

Button btn;
    private Location location;
    private LocationListener locationListener;
    Context context;
    double MyLat,MyLong;
    public Location_Fragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_turn_on_location, container, false);


        btn = (Button) v.findViewById(R.id.turnOn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyCurrentLocation();
            }
        });









        return v;
    }

    boolean gps_enabled,network_enabled;
    @SuppressLint("MissingPermission")
    void getMyCurrentLocation() {


        LocationManager locManager = (LocationManager) getContext().getSystemService( Context.LOCATION_SERVICE );
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
            Location loc = getLastKnownLocation( getContext() );
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
            geocoder = new Geocoder( getContext(), Locale.getDefault() );
            addresses = geocoder.getFromLocation( MyLat, MyLong, 1 );

            //   StateName = addresses.get( 0 ).getAdminArea();
            // CityName = addresses.get( 0 ).getLocality();
            //CountryName = addresses.get( 0 ).getCountryName();
            // you can get more details other than this . like country code, state code, etc.


          /*  Toast.makeText(this," StateName " + StateName+"\n"+
                    " CityName " + CityName+"\n"+
                    " CountryName " + CountryName,Toast.LENGTH_LONG).show();
                    */
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText( getContext(), "" + MyLat + "\n" + MyLong , Toast.LENGTH_LONG ).show();
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