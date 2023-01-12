package com.application.sunbedreservation;

import static android.content.ContentValues.TAG;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the view
        searchView = view.findViewById(R.id.idSearchView);

        //initial camera position coordinates
        LatLng istra = new LatLng(45.28857049078383, 13.889700401795668);
        CameraPosition cp = new CameraPosition.Builder().target(istra).zoom(9).build();

        // start a map with location on Istrian Peninsula
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = SupportMapFragment.newInstance(new GoogleMapOptions().camera(cp));
        fm.beginTransaction().replace(R.id.map, mapFragment).commit();

        // set a query text listener for the search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();

                List<Address> addressList = null;

                if (location != null || location.equals("")) {
                    // on below line we are creating and initializing a geo coder.
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        // on below line we are getting location from the
                        // location name and adding that location to address list.
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // on below line we are getting the location
                    // from our list a first position.
                    Address address = addressList.get(0);

                    // on below line we are creating a variable for our location
                    // where we will add our locations latitude and longitude.
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    // on below line we are adding marker to that position.
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));

                    // below line is to animate camera to that position.
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindowForGoogleMap(getContext()));

        String title = "This is title";
        String subTitle = "This is subtitle";

    //TODO fetch beaches from DB and display them on the screen

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("Beaches");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*if (snapshot.exists()){
                    Beach beach = snapshot.getValue(Beach.class);
                    Log.i("Print out", String.valueOf(snapshot.getChildren()));
                }*/

                for(DataSnapshot beachSnapshot: snapshot.getChildren()) {
                    Log.i("printout", String.valueOf(beachSnapshot.getValue()));
                    Beach beach = beachSnapshot.getValue(Beach.class);
                    Log.i("test", String.valueOf(beach.locationLat));
                    MarkerOptions marker = new MarkerOptions();
                    marker.position(new LatLng(Double.parseDouble(beach.locationLat),Double.parseDouble(beach.locationLng)))
                            .title(beach.title)
                            .snippet(beach.subTitle)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.common_full_open_on_phone));
                    mMap.addMarker(marker);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });



        MarkerOptions markerOpt = new MarkerOptions();
        markerOpt.position(new LatLng(45.28857049078383, 13.889700401795668))
                .title(title)
                .snippet(subTitle)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.common_full_open_on_phone));

        //Set Custom InfoWindow Adapter
        CustomInfoWindowForGoogleMap adapter = new CustomInfoWindowForGoogleMap(getContext());
        mMap.setInfoWindowAdapter(adapter);

        mMap.addMarker(markerOpt).showInfoWindow();
    }
}