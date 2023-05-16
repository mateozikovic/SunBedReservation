package com.application.sunbedreservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class SunbedAdapter extends BaseAdapter {
    private Context context;
    private List<Sunbed> sunbeds;

    public SunbedAdapter(Context context, List<Sunbed> sunbeds) {
        this.context = context;
        this.sunbeds = sunbeds;
    }

    @Override
    public int getCount() {
        return sunbeds.size();
    }

    @Override
    public Object getItem(int position) {
        return sunbeds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_sunbed, parent, false);
        }

        ImageView sunbedImageView = convertView.findViewById(R.id.sunbed_image);

        // Get the Sunbed object for the current position
        Sunbed sunbed = sunbeds.get(position);

        // Set the image resource based on the sunbed state
        int imageResource = sunbed.isTaken() ? R.drawable.sunbed : R.drawable.sunbed;
        sunbedImageView.setImageResource(imageResource);

        return convertView;
    }
}