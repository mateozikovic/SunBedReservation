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
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;


public class SunbedReservationFragment extends Fragment {
    private SunbedReservationViewModel mViewModel;
    private int numSunbeds = 0;

    int[] images = {R.drawable.one,
            R.drawable.two,
            R.drawable.three};

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
                Log.i("parseint", s);
                TextView beachNameTitle = view.findViewById(R.id.beach_name_title);
                // beachNameTitle.setText();

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

        SliderView sliderView = view.findViewById(R.id.image_slider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
        return view;
    }
}