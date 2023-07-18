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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    SearchView searchView;
    private WeatherViewModel weatherViewModel;

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

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("Beaches");

        // Obtain an instance of the ViewModel
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                HashMap<String, Marker> markerMap = new HashMap<>();
                // cycle through documents in beaches collection and display a beach on the map
                for(DataSnapshot beachSnapshot: snapshot.getChildren()) {
                    Beach beach = beachSnapshot.getValue(Beach.class);
                    String beachId = beachSnapshot.getKey();

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(Double.parseDouble(beach.locationLat),Double.parseDouble(beach.locationLng)))
                            .title(beach.title)
                            .snippet(beach.subTitle)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pointer));
                    
                    Map<String,Object> customProperties = new HashMap<>();


                    // Fetch weather data for a specific latitude and longitude
                    double latitude = Double.parseDouble(beach.locationLat); // Replace with the actual latitude
                    double longitude = Double.parseDouble(beach.locationLng); // Replace with the actual longitude
                    weatherViewModel.fetchWeatherData(requireContext(), latitude, longitude);

                    // Observe the weatherData LiveData

                    Marker marker = mMap.addMarker(markerOptions);
                    markerMap.put(beachId, marker);

                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {

                            String documentId = null;
                            for (Map.Entry<String, Marker> entry : markerMap.entrySet()) {
                                if (entry.getValue().equals(marker)) {
                                    documentId = entry.getKey();
                                    break;
                                }
                            }

                            SunbedReservationFragment fragment = new SunbedReservationFragment();

                            //add arguments
                            Bundle bundle = new Bundle();
                            bundle.putString("beach_id", documentId);
                            Log.i("beachkey", String.valueOf(documentId));
                            fragment.setArguments(bundle);

                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                    }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });

        //Set Custom InfoWindow Adapter
        CustomInfoWindowForGoogleMap adapter = new CustomInfoWindowForGoogleMap(getContext());
        mMap.setInfoWindowAdapter(adapter);
    }
}