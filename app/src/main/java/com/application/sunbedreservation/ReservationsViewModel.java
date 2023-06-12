package com.application.sunbedreservation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReservationsViewModel extends ViewModel {
    private MutableLiveData<List<Reservation>> reservationsLiveData;
    private String currentUserId;

    public LiveData<List<Reservation>> getReservationsLiveData() {
        if (reservationsLiveData == null) {
            reservationsLiveData = new MutableLiveData<>();
            loadReservations();
        }
        return reservationsLiveData;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    private void loadReservations() {
        DatabaseReference reservationsRef = FirebaseDatabase.getInstance().getReference("Reservations");

        Query query = reservationsRef.orderByChild("userId").equalTo(currentUserId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Reservation> reservationsList = new ArrayList<>();

                for (DataSnapshot reservationSnapshot : dataSnapshot.getChildren()) {
                    Reservation reservation = reservationSnapshot.getValue(Reservation.class);
                    reservation.setReservationId(reservationSnapshot.getKey());
                    reservationsList.add(reservation);
                }

                reservationsLiveData.setValue(reservationsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any database error
            }
        });
    }

    public void deleteReservation(Reservation reservation) {
        DatabaseReference reservationsRef = FirebaseDatabase.getInstance().getReference("Reservations");
        String reservationId = reservation.getReservationId();

        reservationsRef.child(reservationId).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Deletion successful
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle deletion failure
                    }
                });
    }
}




