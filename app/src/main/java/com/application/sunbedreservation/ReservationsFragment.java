package com.application.sunbedreservation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ReservationsFragment extends Fragment {
    private ReservationsViewModel reservationsViewModel;
    private ReservationsAdapter reservationsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservations, container, false);

        // Initialize the RecyclerView
        RecyclerView reservationsRecyclerView = view.findViewById(R.id.reservationsRecyclerView);
        reservationsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize the reservations adapter
        reservationsAdapter = new ReservationsAdapter();
        reservationsRecyclerView.setAdapter(reservationsAdapter);

        // Initialize the ViewModel
        reservationsViewModel = new ViewModelProvider(this).get(ReservationsViewModel.class);

        // Get the currently logged-in user ID (or any identifier)
        String currentUserId = getCurrentUserId();

        // Set the current user ID in the ViewModel
        reservationsViewModel.setCurrentUserId(currentUserId);

        // Observe the reservations LiveData
        reservationsViewModel.getReservationsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Reservation>>() {
            @Override
            public void onChanged(List<Reservation> reservations) {
                // Update the UI with the new reservations list
                updateReservationsList(reservations);
            }
        });

        return view;
    }

    private String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        } else {
            return null;
        }
    }

    private void updateReservationsList(List<Reservation> reservations) {
        // Update the UI with the new reservations list
        reservationsAdapter.setReservationsList(reservations);
    }
}
