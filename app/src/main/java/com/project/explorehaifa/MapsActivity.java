// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.project.explorehaifa;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    String TAG = "OnMap: ";
    String categoryItemName;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        categoryItemName = intent.getExtras().getString("category item name","");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Log.d(TAG, categoryItemName);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        Geocoder gc = new Geocoder(getApplicationContext());
        List<Address> addresses = null;
        try {
            addresses = gc.getFromLocationName( categoryItemName, 1);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
      //  LatLng dozanRestaurantLoc = new LatLng(32.820018, 34.990654);
        double latitude = 0;
        double longitude = 0;
        if(addresses != null) {

           latitude  = addresses.get(0).getLatitude();
           longitude = addresses.get(0).getLongitude();
        }

        LatLng location = new LatLng(latitude , longitude );
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(categoryItemName));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(dozanRestaurant));
        moveToCurrentLocation(location);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //  googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //googleMap.setTrafficEnabled(true);
    }
    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 6000, null);


    }
}
