package com.project.explorehaifa;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomePage extends AppCompatActivity {

    public static final String KEY_SELECTED = "ExploreHaifa";
    public int selected;
    FragmentManager fragmentManager;
    Fragment currentFragment;
    BottomNavigationView nav;
    String nearByPlacesType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_home_page);
        //Intent currIntent = getIntent();
        final String TAG = "ExpHaifa-HomePage";
        Log.d(TAG, "loading Home page");
        selected = R.id.nav_home;

        nav = findViewById(R.id.nav_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nav.setItemIconTintList(getColorStateList(R.color.navbar));
        }
        currentFragment = new HomeFragment();
        currentFragment.setArguments(getIntent().getExtras());


        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.main_container, currentFragment)
                .commit();

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment targetFragment;
                TextView tv1;
                TextView tv2;

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:

                        targetFragment =new HomeFragment();
                        break;

                    case R.id.nav_profile:{
                        Log.d(TAG, " in profile  " );
                        if(MainActivity.loggedInUser == null)
                        {
//                              tv2 = findViewById(R.id.tv_choose_category);
//                              tv2.setVisibility(View.INVISIBLE);

                            //   Log.d(TAG, "profile name after login: " + MainActivity.loggedInUser.getFullName() );

                            Intent activityIntent = new Intent(getApplicationContext(), SignInActivity.class);
                            startActivity(activityIntent);
                            return  true;

                        }
                        else
                        {
                            Log.d(TAG, "profile name: " +   MainActivity.loggedInUser.getFullName() );
                            targetFragment =new ProfileFragment();
                            break;


                        }

                    }
                    case R.id.nav_nearBy:
                        PopupMenu popup = new PopupMenu(HomePage.this, findViewById(R.id.nav_nearBy));
                        MenuInflater inflater = popup.getMenuInflater();
                        popup.setOnMenuItemClickListener(HomePage.this::onMenuItemClick);
                        inflater.inflate(R.menu.more_menu, popup.getMenu());
                        popup.show();


                    default:
                        return true;
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, targetFragment)
                        .commit();
                currentFragment = targetFragment;
                return true;
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SELECTED, selected);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        selected = savedInstanceState.getInt(KEY_SELECTED, R.id.nav_home);
    }

    @Override
    protected void onResume() {
        super.onResume();
        nav.setSelectedItemId(selected);
    }
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restaurants:

                nearByPlacesType = "Restaurant";
               currentFragment = new ClosePlacesFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, currentFragment)
                        .commit();
                return true;

            case R.id.BusStations:

                nearByPlacesType = "Bus Station";
                currentFragment = new ClosePlacesFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, currentFragment)
                        .commit();
                return true;






            case R.id.touristattraction:

                nearByPlacesType = "Tourist Attraction";
                currentFragment = new ClosePlacesFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, currentFragment)
                        .commit();
                return true;

            case R.id.museum:

                nearByPlacesType = "Museum";
                currentFragment = new ClosePlacesFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_container, currentFragment)
                        .commit();
                return true;
            default:
                return false;
        }}

}


