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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.slider.Slider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

public class SunbedReservationFragment extends Fragment {
    private SunbedReservationViewModel mViewModel;
    private SunbedAdapter sunbedAdapter;
    private FrameLayout datePickerContainer;
    private String selectedDate;

    int[] images = {R.drawable.one, R.drawable.two, R.drawable.three};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunbed_reservation, container, false);
        Button reserveButton = view.findViewById(R.id.reserve_button);

        // Image gallery for the beach
        SliderView sliderView = view.findViewById(R.id.image_slider);
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        Button selectDateButton = view.findViewById(R.id.select_date_button);
        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Initialize the datePickerContainer
        datePickerContainer = view.findViewById(R.id.date_picker_container);

        // Initialize the ViewModel
        mViewModel = new ViewModelProvider(this).get(SunbedReservationViewModel.class);

        // Getting arguments from HomeFragment
        String sunbedId = getArguments().getString("beach_id");

        // Observe user ID
        mViewModel.getUserId().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String uid) {
                reserveButton.setEnabled(true); // Enable the Reserve button
            }
        });

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

        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDate != null) {
                    // Get the selected sunbed from the adapter
                    Sunbed selectedSunbed = sunbedAdapter.getSelectedSunbed();
                    if (selectedSunbed != null) {
                        // Create a reservation object
                        Reservation reservation = new Reservation();
                        reservation.setSunbedId(selectedSunbed.getId());
                        reservation.setReservationDate(selectedDate);

                        // Get the beach ID from the fragment arguments
                        String beachId = getArguments().getString("beach_id");

                        // Save the reservation using the ViewModel and pass the beach ID
                        mViewModel.saveReservation(beachId, reservation);
                    } else {
                        Toast.makeText(getContext(), "Please select a sunbed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Please select a date", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void showDatePicker() {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        Calendar today = Calendar.getInstance();
        today.clear(Calendar.HOUR_OF_DAY);
        today.clear(Calendar.MINUTE);
        today.clear(Calendar.SECOND);
        constraintsBuilder.setValidator(DateValidatorPointForward.from(today.getTimeInMillis()));

        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Date");
        builder.setCalendarConstraints(constraintsBuilder.build());

        final MaterialDatePicker<Long> materialDatePicker = builder.build();
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selectedDate) {
                // Handle the selected date
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(selectedDate);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = dateFormat.format(calendar.getTime());

                // Save the selected date
                SunbedReservationFragment.this.selectedDate = formattedDate;

                // Update the UI to display the selected date
                Button selectDateButton = getView().findViewById(R.id.select_date_button);
                selectDateButton.setText(formattedDate);
            }
        });

        materialDatePicker.show(requireActivity().getSupportFragmentManager(), "DATE_PICKER");
    }
}
