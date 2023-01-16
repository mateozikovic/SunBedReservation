package com.application.sunbedreservation;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StoreDataInFirebase {
    public void writeNewBeach(String title, String subTitle, String picture, String locationLat, String locationLng, String freeSunbeds){
        Beach beach = new Beach(title, subTitle, locationLat, locationLng, freeSunbeds);

        // save a beach to the database with a unique key
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.push().getKey();

        mDatabase.child("Beaches").child(key).setValue(beach);
    }
}


