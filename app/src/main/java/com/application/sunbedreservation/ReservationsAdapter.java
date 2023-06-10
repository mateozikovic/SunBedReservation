package com.application.sunbedreservation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder> {
    private List<Reservation> reservationsList;

    public void setReservationsList(List<Reservation> reservationsList) {
        this.reservationsList = reservationsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation reservation = reservationsList.get(position);
        holder.bind(reservation);
    }

    @Override
    public int getItemCount() {
        return reservationsList != null ? reservationsList.size() : 0;
    }

    static class ReservationViewHolder extends RecyclerView.ViewHolder {
        private TextView beachTitleTextView;
        private TextView reservationDateTextView;

        ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            beachTitleTextView = itemView.findViewById(R.id.beachTitleTextView);
            reservationDateTextView = itemView.findViewById(R.id.reservationDateTextView);
        }

        void bind(Reservation reservation) {
            beachTitleTextView.setText(reservation.getBeachTitle());
            reservationDateTextView.setText(reservation.getReservationDate());
        }
    }
}


