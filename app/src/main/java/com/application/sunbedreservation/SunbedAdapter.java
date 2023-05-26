package com.application.sunbedreservation;

import android.content.Context;
import android.graphics.Color;
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

    public SunbedAdapter(Context context, List<Sunbed> sunbedList) {
        this.context = context;
        this.sunbedList = sunbedList;
        this.selectedSunbeds = new ArrayList<>();
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
        int imageResource = sunbed.isTaken() ? R.drawable.sunbed_taken : R.drawable.sunbed_free;
        holder.sunbedImageView.setImageResource(imageResource);

        // Set the background color based on sunbed selection
        int backgroundColor = selectedSunbeds.contains(sunbed) ? R.color.selected_sunbed_color : android.R.color.transparent;
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, backgroundColor));

        // Set the click listener on the sunbed item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedSunbeds.contains(sunbed)) {
                    selectedSunbeds.remove(sunbed);
                } else {
                    selectedSunbeds.add(sunbed);
                }

                notifyDataSetChanged(); // Update the item view

                // Perform further actions on sunbed selection if needed
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

    static class SunbedViewHolder extends RecyclerView.ViewHolder {
        ImageView sunbedImageView;

        public SunbedViewHolder(@NonNull View itemView) {
            super(itemView);
            sunbedImageView = itemView.findViewById(R.id.sunbed_image);
        }
    }
}


