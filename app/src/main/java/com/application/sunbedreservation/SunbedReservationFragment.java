package com.application.sunbedreservation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class SunbedReservationFragment extends Fragment {
    private SunbedReservationViewModel mViewModel;
    private int numSunbeds = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunbed_reservation, container, false);

        // getting arguments from HomeFragment
        String sunbedId = getArguments().getString("beach_id");

        // send the beachId to the viewmodel, and get back the number of sunbeds from the DB
        mViewModel = new SunbedReservationViewModel(sunbedId);
        mViewModel.getData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                numSunbeds = Integer.parseInt(s);
                Log.i("fragment sunbeds", String.valueOf(numSunbeds));

                LinearLayout sunbedContainer = view.findViewById(R.id.sunbed_container);
                for (int i = 0; i < numSunbeds; i++) {
                    ImageView sunbed = new ImageView(getContext());
                    sunbed.setImageResource(R.drawable.sunbed);
                    sunbed.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    sunbedContainer.addView(sunbed);
                }
            }
        });
        return view;
    }
}