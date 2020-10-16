package com.project.explorehaifa;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.RankBy;

// import com.google.maps.model.LatLng;

// import com.google.android.gms.maps.model.LatLng;

public class ClosePlacesFragment extends Fragment implements OnMapReadyCallback {
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    SupportMapFragment supportMapFragment;
    com.google.android.gms.maps.model.LatLng myPosLatLng;
    PlacesSearchResult[] placesSearchResults;
    private static final int REQUEST_CODE = 101;
    HomePage parent;
    final String TAG = "ExpHaifa-NEARBY";
    public ClosePlacesFragment() {
        super();
    }
    View myView;
    @Nullable
    @Override
    public View  onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedIns) {
        parent = (HomePage) getActivity();
        Log.d(TAG, "loading Home page Fragment on create");
        myView = inflater.inflate(R.layout.fragment_maps_close_places, container, false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fetchLocation();
        return myView;

    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getActivity(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_close_places);
                    assert supportMapFragment != null;

                    new NearbyPlacesAsyncTask().execute("test");

                    //supportMapFragment.getMapAsync(ClosePlacesFragment.this);
                }
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        // mMap.moveCamera(CameraUpdateFactory.newLatLng(dozanRestaurant));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //  googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //googleMap.setTrafficEnabled(true);


        // LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        // MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("המיקום שלי");
        // googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        // googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        // googleMap.addMarker(markerOptions);

//        com.google.android.gms.maps.model.LatLng myPosLatLng =
//                new com.google.android.gms.maps.model.LatLng(
//                        currentLocation.getLatitude(), currentLocation.getLongitude());
        Marker myMarker = googleMap.addMarker(new MarkerOptions()
                .position(myPosLatLng)
                .title("המיקום שלי"));
        myMarker.showInfoWindow();
        moveToCurrentLocation(myPosLatLng, googleMap);


//        PlacesSearchResult[] placesSearchResults = getNearbyPlaces(myPosLatLng).results;
//
//        Log.e("response1Tag", placesSearchResults[0].toString());
//        Log.e("response2Tag", placesSearchResults[1].toString());
//
        for (int i = 0; i < 50 && i < placesSearchResults.length; i++) {
            double lati = placesSearchResults[i].geometry.location.lat;
            double lngi = placesSearchResults[i].geometry.location.lng;

            String placeName = placesSearchResults[i].name;
//            double lat2 = placesSearchResults[1].geometry.location.lat;
//            double lng2 = placesSearchResults[1].geometry.location.lng;

            Marker marker = googleMap.addMarker(new MarkerOptions()
                                    .position(new com.google.android.gms.maps.model.LatLng(lati, lngi))
                                    .title(placeName));

            //marker.showInfoWindow();
//            googleMap.addMarker(new MarkerOptions().position(new com.google.android.gms.maps.model.LatLng(lat2, lng2)));
        }
    }

    public PlacesSearchResponse getNearbyPlaces(com.google.android.gms.maps.model.LatLng latLng) {
        String nearByPlacesType = parent.nearByPlacesType;
        PlacesSearchResponse request = new PlacesSearchResponse();
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyArRiqWFQ19uALx0njmayGImh2LshldVHc")
                .build();

        com.google.maps.model.LatLng location =
                new com.google.maps.model.LatLng(latLng.latitude, latLng.longitude);

        try {
            if(nearByPlacesType.equals("Restaurant")) {
                request = PlacesApi.nearbySearchQuery(context, location)
                        .radius(5000)
                        .rankby(RankBy.PROMINENCE)
//                    .keyword("cruise")
//                    .language("en")
                        .type(PlaceType.RESTAURANT)
                        .await();
            }
            else if (nearByPlacesType.equals("Bus Station"))
            {
                request = PlacesApi.nearbySearchQuery(context, location)
                        .radius(5000)
                        .rankby(RankBy.PROMINENCE)
//                    .keyword("cruise")
//                    .language("en")
                        .type(PlaceType.BUS_STATION)
                        .await();
            }

            else if (nearByPlacesType.equals("Museum"))
            {
                request = PlacesApi.nearbySearchQuery(context, location)
                        .radius(5000)
                        .rankby(RankBy.PROMINENCE)
//                    .keyword("cruise")
//                    .language("en")
                        .type(PlaceType.MUSEUM)
                        .await();
            }
            else if (nearByPlacesType.equals("Tourist Attraction"))
            {
                request = PlacesApi.nearbySearchQuery(context, location)
                        .radius(5000)
                        .rankby(RankBy.PROMINENCE)
//                    .keyword("cruise")
//                    .language("en")
                        .type(PlaceType.TOURIST_ATTRACTION)
                        .await();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error: failed to get locations nearby you. " + e.getMessage());
            e.printStackTrace();
        }
        finally {
            return request;
        }
    }
    private void moveToCurrentLocation(com.google.android.gms.maps.model.LatLng currentLocation,GoogleMap googleMap)
    {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,13));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13), 3000, null);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }



    private class NearbyPlacesAsyncTask extends AsyncTask<String, Integer, Long> {
        protected Long doInBackground(String... params) {
            int count = params.length;

            myPosLatLng =
                    new com.google.android.gms.maps.model.LatLng(
                            currentLocation.getLatitude(), currentLocation.getLongitude());


            placesSearchResults = getNearbyPlaces(myPosLatLng).results;


            return 0L;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Long result) {
            // showDialog("Downloaded " + result + " bytes");
            supportMapFragment.getMapAsync(ClosePlacesFragment.this);
        }
    }


}






