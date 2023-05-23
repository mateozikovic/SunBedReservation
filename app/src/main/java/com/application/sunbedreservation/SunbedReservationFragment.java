package com.application.sunbedreservation;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;


public class SunbedReservationFragment extends Fragment {
    private SunbedReservationViewModel mViewModel;
    private SunbedAdapter sunbedAdapter;
    private FrameLayout datePickerContainer;

    int[] images = {R.drawable.one,
            R.drawable.two,
            R.drawable.three};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunbed_reservation, container, false);

        Button selectDateButton = view.findViewById(R.id.select_date_button);
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Initialize the datePickerContainer
        datePickerContainer = view.findViewById(R.id.date_picker_container);

        // Create the MaterialDatePicker
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Date");

        final MaterialDatePicker<Long> materialDatePicker = builder.build();

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selectedDate) {
                // Handle the selected date here
                // You can update the UI or perform any other logic with the selected date
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(selectedDate);
                // Do something with the selected date
            }
        });

        // Add the MaterialDatePicker to the datePickerContainer
        Dialog datePickerDialog = materialDatePicker.getDialog();
        if (datePickerDialog != null) {
            View datePickerView = datePickerDialog.findViewById(android.R.id.content);
            if (datePickerView != null) {
                ViewGroup parent = (ViewGroup) datePickerView.getParent();
                if (parent != null) {
                    parent.removeView(datePickerView);
                }
                datePickerContainer.addView(datePickerView);
            }
        }
        // Getting arguments from HomeFragment
        String sunbedId = getArguments().getString("beach_id");

        // Initialize the ViewModel
        mViewModel = new ViewModelProvider(this).get(SunbedReservationViewModel.class);

        // Fetch the list of sunbeds
        mViewModel.fetchSunbeds(sunbedId);
        mViewModel.getSunbedRows().observe(getViewLifecycleOwner(), new Observer<Map<Integer, List<Sunbed>>>() {
            @Override
            public void onChanged(Map<Integer, List<Sunbed>> sunbedMap) {
                // Convert sunbedMap to a flat list of sunbeds
                List<Sunbed> sunbedList = new ArrayList<>();
                for (List<Sunbed> row : sunbedMap.values()) {
                    sunbedList.addAll(row);
                }

                // Set up the RecyclerView
                RecyclerView recyclerView = view.findViewById(R.id.sunbed_recyclerview);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4, GridLayoutManager.HORIZONTAL, false));
                sunbedAdapter = new SunbedAdapter(getContext(), sunbedList);
                recyclerView.setAdapter(sunbedAdapter);
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

    private void showDatePicker() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Date");

        final MaterialDatePicker<Long> materialDatePicker = builder.build();
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selectedDate) {
                // Handle the selected date here
                // You can update the UI or perform any other logic with the selected date
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(selectedDate);
                // Do something with the selected date
            }
        });

        materialDatePicker.show(getParentFragmentManager(), "DATE_PICKER_TAG");
    }
}