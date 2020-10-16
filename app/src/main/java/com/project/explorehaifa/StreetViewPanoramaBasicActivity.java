

package com.project.explorehaifa;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * This shows how to create a simple activity with streetview
 */
public class StreetViewPanoramaBasicActivity extends AppCompatActivity {


    private static LatLng location;
    String TAG = "OnShowStreet: ";
    String categoryItemName;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.street_view_panorama_basic);
        Intent intent = getIntent();
        categoryItemName = intent.getExtras().getString("category item name","");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Log.d(TAG, categoryItemName);
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
        final LatLng location = new LatLng(latitude , longitude );

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment)
                        getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                        // Only set the panorama to SYDNEY on startup (when no panoramas have been
                        // loaded which is when the savedInstanceState is null).
                        if (savedInstanceState == null) {
                            panorama.setPosition(location);
                        }
                    }
                });
    }
}
