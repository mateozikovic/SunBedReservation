package com.application.sunbedreservation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReservationsViewModel extends ViewModel {
    private String currentUserId;
    private MutableLiveData<List<Reservation>> reservationsLiveData;

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
        loadReservations();
    }

    public LiveData<List<Reservation>> getReservationsLiveData() {
        if (reservationsLiveData == null) {
            reservationsLiveData = new MutableLiveData<>();
            loadReservations();
        }
        return reservationsLiveData;
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
}



