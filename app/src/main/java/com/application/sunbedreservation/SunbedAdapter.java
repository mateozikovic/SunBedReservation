package com.application.sunbedreservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class SunbedAdapter extends RecyclerView.Adapter<SunbedAdapter.SunbedViewHolder> {
    private Context context;
    private Map<Integer, List<Sunbed>> sunbedMap;

    public SunbedAdapter(Context context, Map<Integer, List<Sunbed>> sunbedMap) {
        this.context = context;
        this.sunbedMap = sunbedMap;
    }

    @NonNull
    @Override
    public SunbedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sunbed, parent, false);
        return new SunbedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SunbedViewHolder holder, int position) {
        // Find the row and index of the sunbed in the map
        int rowIndex = position / 2;
        int indexInRow = position % 2;

        // Get the list of sunbeds for the row
        List<Sunbed> sunbeds = sunbedMap.get(rowIndex);

        if (sunbeds != null && indexInRow < sunbeds.size()) {
            // Get the Sunbed object for the current position
            Sunbed sunbed = sunbeds.get(indexInRow);

            // Set the image resource based on the sunbed state
            int imageResource = sunbed.isTaken() ? R.drawable.sunbed : R.drawable.sunbed;
            holder.sunbedImageView.setImageResource(imageResource);
        }
    }

    @Override
    public int getItemCount() {
        int totalCount = 0;

        for (List<Sunbed> sunbeds : sunbedMap.values()) {
            totalCount += sunbeds.size();
        }

        return totalCount;
    }

    static class SunbedViewHolder extends RecyclerView.ViewHolder {
        ImageView sunbedImageView;

        public SunbedViewHolder(@NonNull View itemView) {
            super(itemView);
            sunbedImageView = itemView.findViewById(R.id.sunbed_image);
        }
    }
}