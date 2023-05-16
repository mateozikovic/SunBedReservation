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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

public class SunbedReservationFragment extends Fragment {
    private SunbedReservationViewModel mViewModel;
    private SunbedAdapter sunbedAdapter;

    int[] images = {R.drawable.one,
            R.drawable.two,
            R.drawable.three};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunbed_reservation, container, false);

        // getting arguments from HomeFragment
        String sunbedId = getArguments().getString("beach_id");

        // Initialize the ViewModel
        mViewModel = new ViewModelProvider(this).get(SunbedReservationViewModel.class);

        // Fetch the list of sunbeds
        mViewModel.fetchSunbeds(sunbedId);
        mViewModel.getSunbedList().observe(getViewLifecycleOwner(), new Observer<List<Sunbed>>() {
            @Override
            public void onChanged(List<Sunbed> sunbeds) {
                // Set up the GridView
                GridView gridView = view.findViewById(R.id.sunbed_gridview);
                sunbedAdapter = new SunbedAdapter(getContext(), sunbeds);
                gridView.setAdapter(sunbedAdapter);
            }
        });

        SliderView sliderView = view.findViewById(R.id.image_slider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
        return view;
    }
}