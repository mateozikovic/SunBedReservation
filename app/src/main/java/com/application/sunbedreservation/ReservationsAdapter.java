package com.application.sunbedreservation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder> {
    private List<Reservation> reservationsList;
    private OnDeleteReservationClickListener onDeleteReservationClickListener;

    public void setReservationsList(List<Reservation> reservationsList) {
        this.reservationsList = reservationsList;
        notifyDataSetChanged();
    }

    public void setOnDeleteReservationClickListener(OnDeleteReservationClickListener listener) {
        this.onDeleteReservationClickListener = listener;
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

    class ReservationViewHolder extends RecyclerView.ViewHolder {
        private TextView beachTitleTextView;
        private TextView reservationDateTextView;
        private TextView sunbedsTextView;
        private Button deleteReservationButton;

        ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            beachTitleTextView = itemView.findViewById(R.id.beachTitleTextView);
            reservationDateTextView = itemView.findViewById(R.id.reservationDateTextView);
            sunbedsTextView = itemView.findViewById(R.id.sunbedsTextView);
            deleteReservationButton = itemView.findViewById(R.id.deleteReservationButton);
        }

        void bind(Reservation reservation) {
            beachTitleTextView.setText(reservation.getBeachTitle());
            reservationDateTextView.setText("Reservation date: " + reservation.getReservationDate());

            List<String> sunbedIds = reservation.getSunbedIds();
            String sunbedsText = TextUtils.join(", ", sunbedIds);
            sunbedsTextView.setText("Reserved sunbeds: " + sunbedsText);

            deleteReservationButton.setOnClickListener(v -> {
                if (onDeleteReservationClickListener != null) {
                    onDeleteReservationClickListener.onDeleteReservationClick(reservation);
                }
            });
        }
    }

    public interface OnDeleteReservationClickListener {
        void onDeleteReservationClick(Reservation reservation);
    }
}


