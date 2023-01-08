package com.application.sunbedreservation;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StoreDataInFirebase {
    public void writeNewBeach(String title, String subTitle, String picture, String location, String freeSunbeds){
        Beach beach = new Beach(title, subTitle, picture, location, freeSunbeds);

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("beaches").setValue(beach);
    }
}


