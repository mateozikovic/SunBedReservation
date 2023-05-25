package com.application.sunbedreservation;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SunbedReservationViewModel extends ViewModel {
    private MutableLiveData<Map<Integer, List<Sunbed>>> sunbedRows = new MutableLiveData<>();
    private MutableLiveData<String> currentUserId = new MutableLiveData<>();

    public LiveData<Map<Integer, List<Sunbed>>> getSunbedRows() {
        return sunbedRows;
    }

    public LiveData<String> getUserId() {
        return currentUserId;
    }
    public void fetchSunbeds(String sunbedId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Beaches").child(sunbedId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<Integer, List<Sunbed>> sunbedMap = new HashMap<>();

                for (DataSnapshot childSnapshot : snapshot.child("sunbeds").getChildren()) {
                    Sunbed sunbed = childSnapshot.getValue(Sunbed.class);
                    if (sunbed != null) {
                        int row = sunbed.getRow();
                        if (!sunbedMap.containsKey(row)) {
                            sunbedMap.put(row, new ArrayList<>());
                        }
                        sunbedMap.get(row).add(sunbed);
                    }
                }
                sunbedRows.setValue(sunbedMap);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Failed to get data from Firebase", error.getMessage());
            }
        });
    }

    private String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        } else {
            return null;
        }
    }

    public void saveReservation(String beachId, Reservation reservation) {
        String userId = getCurrentUserId();

        if (userId != null) {
            DatabaseReference reservationsRef = FirebaseDatabase.getInstance().getReference("Reservations");
            String reservationId = reservationsRef.push().getKey();

            if (reservationId != null) {
                reservation.setUserId(userId);
                reservation.setBeachId(beachId); // Set the selected beach ID
                reservationsRef.child(reservationId).setValue(reservation)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Reservation saved successfully
                                Log.d("Reservation", "Reservation saved with ID: " + reservationId);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to save reservation
                                Log.e("Reservation", "Error saving reservation", e);
                            }
                        });
            }
        } else {
            // Unable to get current user ID
            Log.e("Reservation", "Unable to get current user ID");
        }
    }
}

