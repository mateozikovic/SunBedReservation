package com.application.sunbedreservation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SunbedReservationViewModel extends ViewModel {
    private MutableLiveData<Integer> numSunbeds = new MutableLiveData<>();
    private String sunbedId;

    public SunbedReservationViewModel(String sunbedId) {
        this.sunbedId = sunbedId;
        fetchNumSunbeds();
    }

    private MutableLiveData<String> mData = new MutableLiveData<>();

    public LiveData<String> getData() {
        return mData;
    }

    private void fetchNumSunbeds() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Beaches").child(sunbedId).child("freeSunbeds");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data = snapshot.getValue(String.class);
                mData.setValue(data);
                Log.i("sunbed number", data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Failed to get data from Firebase", error.getMessage());
            }
        });
    }
}
