package com.application.sunbedreservation;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SunbedAdapter extends RecyclerView.Adapter<SunbedAdapter.SunbedViewHolder> {
    private Context context;
    private List<Sunbed> sunbedList;
    private List<Sunbed> selectedSunbeds;
    private String selectedDate;

    public SunbedAdapter(Context context, List<Sunbed> sunbedList, String selectedDate) {
        this.context = context;
        this.sunbedList = sunbedList;
        this.selectedSunbeds = new ArrayList<>();
        this.selectedDate = selectedDate;
    }

    @NonNull
    @Override
    public SunbedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sunbed, parent, false);
        return new SunbedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SunbedViewHolder holder, int position) {
        Sunbed sunbed = sunbedList.get(position);

        // Set the image resource based on the sunbed state
        if (isSunbedReserved(sunbed)) {
            holder.sunbedImageView.setImageResource(R.drawable.sunbed_taken);
        } else {
            holder.sunbedImageView.setImageResource(R.drawable.sunbed_free);
        }

        // Set the background color based on sunbed selection (if not reserved)
        int backgroundColor = selectedSunbeds.contains(sunbed) ? R.color.selected_sunbed_color : android.R.color.transparent;
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, backgroundColor));

        // Set the click listener on the sunbed item (if not reserved)
        holder.itemView.setClickable(!isSunbedReserved(sunbed));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSunbedReserved(sunbed)) {
                    if (selectedSunbeds.contains(sunbed)) {
                        selectedSunbeds.remove(sunbed);
                    } else {
                        selectedSunbeds.add(sunbed);
                    }
                    notifyDataSetChanged(); // Update the item view

                    // Log the reservation dates for debugging
                    Log.d("SunbedAdapter", "Sunbed ID: " + sunbed.getId() + ", Reserved Dates: " + sunbed.getReservedDates());

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sunbedList.size();
    }

    public List<Sunbed> getSelectedSunbeds() {
        return selectedSunbeds;
    }

    private boolean isSunbedReserved(Sunbed sunbed) {
        Log.d("SunbedAdapter", "Checking sunbed ID: " + sunbed.getId());
        Log.d("SunbedAdapter", "Selected Date: " + selectedDate);
        List<String> reservedDates = sunbed.getReservedDates();
        Log.d("SunbedAdapter", "Reserved Dates: " + reservedDates);

        boolean isReserved = reservedDates != null && reservedDates.contains(selectedDate);
        Log.d("SunbedAdapter", "Is Reserved: " + isReserved);

        return isReserved;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
        notifyDataSetChanged(); // Refresh the RecyclerView items
    }


    static class SunbedViewHolder extends RecyclerView.ViewHolder {
        ImageView sunbedImageView;

        public SunbedViewHolder(@NonNull View itemView) {
            super(itemView);
            sunbedImageView = itemView.findViewById(R.id.sunbed_image);
        }
    }
}



