package com.application.sunbedreservation;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Map;

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
        TextView freeSunbeds = (TextView) v.findViewById(R.id.info_free_sunbeds);
        TextView temperature = (TextView) v.findViewById(R.id.info_temperature);
        ImageView image = (ImageView) v.findViewById(R.id.info_window_image);

        title.setText(marker.getTitle());
        subtitle.setText(marker.getSnippet());

        Map<String, Object> customProperties = (Map<String, Object>) marker.getTag();
        freeSunbeds.setText((String) customProperties.get("customString"));

        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
