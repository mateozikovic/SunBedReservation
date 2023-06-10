package com.application.sunbedreservation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ReservationsFragment extends Fragment {
    private ReservationsViewModel reservationsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservations, container, false);

        reservationsViewModel = new ViewModelProvider(this).get(ReservationsViewModel.class);

        // Get the currently logged-in user ID (or any identifier)
        String currentUserId = getCurrentUserId(); // Implement this method to get the user ID

        reservationsViewModel.setCurrentUserId(currentUserId);
        reservationsViewModel.getReservationsLiveData().observe(getViewLifecycleOwner(), reservations -> {
            // Update the UI with the new reservations list
            updateReservationsList(reservations);
        });

        return view;
    }

    private void updateReservationsList(List<Reservation> reservations) {
        // Update the UI with the new reservations list
        // For example, update the RecyclerView adapter with the new reservations data
    }

    private String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        } else {
            return null;
        }
    }
}
