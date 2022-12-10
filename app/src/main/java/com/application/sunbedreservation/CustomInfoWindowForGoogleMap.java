package com.application.sunbedreservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowForGoogleMap implements GoogleMap.InfoWindowAdapter {

    Context context;
    LayoutInflater inflater;

    public CustomInfoWindowForGoogleMap(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.custom_info_window, null);

        TextView title = (TextView) v.findViewById(R.id.info_title);
        TextView subtitle = (TextView) v.findViewById(R.id.info_subtitle);

        title.setText(marker.getTitle());
        subtitle.setText(marker.getSnippet());

        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
