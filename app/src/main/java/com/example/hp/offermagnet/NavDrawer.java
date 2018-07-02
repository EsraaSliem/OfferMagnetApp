package com.example.hp.offermagnet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hp.offermagnet.services.NotificationService;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

//import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class NavDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    CircleImageView imageView;
    Fragment fragment;
    FragmentManager fragmentManager;
    TextView name;
    Database db;
    EditText search_text;
    Button do_search;
    SharedPreferences.Editor prefEditor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        startService(new Intent(this, NotificationService.class));

        fragmentManager = getSupportFragmentManager();
        db = new Database(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (TextView) findViewById(R.id.name);

        search_text= findViewById(R.id.search_text);
        do_search= findViewById(R.id.do_search);
        do_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragment = new HomeFragment();
                Bundle bundle= new Bundle();
                bundle.putString("srchTxt", search_text.getText().toString());
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.container_body, fragment).addToBackStack( "home" ).commit();

            }
        });

        prefEditor= getSharedPreferences("AppPrefrences", Context.MODE_PRIVATE).edit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        name = (TextView) findViewById(R.id.yourname);

        imageView = view.findViewById(R.id.imageProfilee);
        //if (imageView != null)
        Picasso.with(this)
                .load(db.getImage())
                .into(imageView);
        //name.append(db.getName());

        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragment = new HomeFragment();
        Bundle bundle= new Bundle();
        bundle.putString("srchTxt", "");
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container_body, fragment).addToBackStack( "home" ).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Home) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragment = new HomeFragment();
            Bundle bundle= new Bundle();
            bundle.putString("srchTxt", "");
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.container_body, fragment).addToBackStack( "home" ).commit();

        } else if (id == R.id.category) {

        } else if (id == R.id.wishlist) {
            fragment = new WishlistFragment();
            fragmentManager.beginTransaction();
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container_body, fragment).addToBackStack( "wishlist" ).commit();
        } else if (id == R.id.myOrder) {
            fragment = new MyOrderFragment();
            fragmentManager.beginTransaction();
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container_body, fragment).addToBackStack( "myorder" ).commit();

        } else if (id == R.id.MyProfile) {
            fragment = new ProfileFragment();
            fragmentManager.beginTransaction();
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container_body, fragment).addToBackStack( "myprofile" ).commit();

        }        else if (id==R.id.privacy) {
        fragment = new PrivacyFragment();
        fragmentManager.beginTransaction();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container_body, fragment).addToBackStack( "myprofile" ).commit();

    }
        else if (id==R.id.logout) {

            Intent intent = new Intent(NavDrawer.this,UserTab.class );
            prefEditor.putBoolean("logined",false);
            prefEditor.apply();
            startActivity(intent);
        }
        else if (id==R.id.help) {
            fragment = new FQFragment();
            fragmentManager.beginTransaction();
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container_body, fragment).addToBackStack( "myprofile" ).commit();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
