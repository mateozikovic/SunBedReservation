package com.application.sunbedreservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.application.sunbedreservation.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.reservations:
                    replaceFragment(new ReservationsFragment());
                    break;
                case R.id.news:
                    replaceFragment(new NewsFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }

            return true;
        });

         // this is used just to fill the database with dummy data

//         StoreDataInFirebase fillDB = new StoreDataInFirebase();
//        fillDB.writeNewBeach("Zlatne Stijene", "Lijepa plaza", "pic.jpg",
//                "44.84640377977642", "13.833914353971652", "20");
//        fillDB.writeNewBeach("Pical Beach", "Plaza u blizini hotela Parentino", "pic.jpg",
//                "45.23498102948223", "13.596537451227437", "15");
//        fillDB.writeNewBeach("Materada Beach", "Plaza u blizini hotela Materada", "pic.jpg",
//                "45.24926887641833", "13.591844956120799", "25");

        /*
        * TODO create a sub-document in each beach for beach prices and fill it with dummy data
        * */
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference beachesRef = database.getReference("Beaches");

        beachesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot beachSnapshot: snapshot.getChildren()){
                    Log.i("beachtag", String.valueOf(beachSnapshot.getKey()));
                    // get the document ID
                    String documentID = beachSnapshot.getKey();

                    // set the prices for all beaches
                    SunbedPrices sunbedPrices = new SunbedPrices();
                    sunbedPrices.setRow1June(20);
                    sunbedPrices.setRow2June(18);
                    sunbedPrices.setRow1July(25);
                    sunbedPrices.setRow2July(23);
                    sunbedPrices.setRow1August(27);
                    sunbedPrices.setRow2August(25);
                    sunbedPrices.setRow1September(18);
                    sunbedPrices.setRow2September(16);

                    beachesRef.child(documentID).child("BeachPrices").setValue(sunbedPrices);


//                    Sunbed[] sunbeds = {
//                            new Sunbed("sunbed1", 1, false),
//                            new Sunbed("sunbed2", 1, false),
//                            new Sunbed("sunbed3", 1, false),
//                            new Sunbed("sunbed4", 1, false),
//                            new Sunbed("sunbed5", 2, false),
//                            new Sunbed("sunbed6", 2, false),
//                            new Sunbed("sunbed7", 2, false),
//                            new Sunbed("sunbed8", 2, false)
//                    };
//
//                    for (Sunbed sunbed : sunbeds) {
//                        String beachId = beachesRef.child("beaches").push().getKey();
//                        beachesRef.child(documentID).child("sunbeds").child(sunbed.getId()).setValue(sunbed);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("BeachPrice Error", String.valueOf(error));
            }
        });


    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}