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
    private List<Sunbed> sunbedList;

    public SunbedAdapter(Context context, List<Sunbed> sunbedList) {
        this.context = context;
        this.sunbedList = sunbedList;
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
    }

    @Override
    public int getItemCount() {
        return sunbedList.size();
    }

    static class SunbedViewHolder extends RecyclerView.ViewHolder {
        ImageView sunbedImageView;

        public SunbedViewHolder(@NonNull View itemView) {
            super(itemView);
            sunbedImageView = itemView.findViewById(R.id.sunbed_image);
        }
    }
}