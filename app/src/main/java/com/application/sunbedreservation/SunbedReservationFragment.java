package com.application.sunbedreservation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

public class SunbedReservationFragment extends Fragment {
    private SunbedReservationViewModel mViewModel;
    private WeatherViewModel weatherViewModel;
    private SunbedAdapter sunbedAdapter;
    private FrameLayout datePickerContainer;
    private String selectedDate;
    private MutableLiveData<Double> totalPriceLiveData;
    private TextView titleTextView;
    private TextView subTitleTextView;
    private TextView temperature;
    private TextView humidity;
    private TextView weatherDescription;

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
        String beachId = getArguments().getString("beach_id");

        mViewModel.fetchBeachData(beachId);

        DatabaseReference dbReferenceWeather;
        dbReferenceWeather = FirebaseDatabase.getInstance().getReference("Beaches").child(beachId);

        dbReferenceWeather.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Beach beach = snapshot.getValue(Beach.class);

                Double lat = Double.parseDouble(beach.locationLat);
                Double lng = Double.parseDouble(beach.locationLng);

                weatherViewModel.fetchWeatherData(requireContext(), lat, lng);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        temperature = view.findViewById(R.id.beach_temperature);
        humidity = view.findViewById(R.id.beach_humidity);
        weatherDescription = view.findViewById(R.id.beach_weather_description);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        // Observe the weatherData LiveData
        weatherViewModel.getWeatherData().observe(getViewLifecycleOwner(), weatherData -> {
            if (weatherData != null) {

                String weatherDataInfoString = ("Temperature: " + weatherData.getTemperature() + "\n" +
                        "Humidity: " + weatherData.getHumidity() + "\n" +
                        "Weather Description: " + weatherData.getWeatherDescription());

                temperature.setText(String.valueOf(weatherData.getTemperature()));
                humidity.setText(String.valueOf(weatherData.getHumidity()));
                weatherDescription.setText(String.valueOf(weatherData.getWeatherDescription()));

                // Log the weather data
                Log.d("WeatherData1", "Temperature: " + weatherData.getTemperature() + "°C");
                Log.d("WeatherData1", "Humidity: " + weatherData.getHumidity() + "%");
                Log.d("WeatherData1", "Weather Description: " + weatherData.getWeatherDescription());

            } else {

            }
        });

        // Observe user ID
        mViewModel.getUserId().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String uid) {
                reserveButton.setEnabled(true); // Enable the Reserve button
            }
        });

        titleTextView = view.findViewById(R.id.beach_name_title);
        subTitleTextView = view.findViewById(R.id.beach_sub_title);

        // Observe LiveData from the ViewModel
        mViewModel.getTitleLiveData().observe(getViewLifecycleOwner(), title -> {
            // Update the titleTextView with the new title value
            titleTextView.setText(title);
        });

        mViewModel.getSubTitleLiveData().observe(getViewLifecycleOwner(), subTitle -> {
            // Update the subTitleTextView with the new subTitle value
            subTitleTextView.setText(subTitle);
        });

        mViewModel.fetchBeachData(beachId);
        
        // Fetch the list of sunbeds
        mViewModel.fetchSunbeds(beachId);
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
                    // Get the selected sunbeds from the adapter
                    List<Sunbed> selectedSunbeds = sunbedAdapter.getSelectedSunbeds();
                    if (!selectedSunbeds.isEmpty()) {
                        // Create a reservation object
                        Reservation reservation = new Reservation();
                        List<String> sunbedIds = new ArrayList<>();
                        for (Sunbed sunbed : selectedSunbeds) {
                            sunbedIds.add(sunbed.getId());
                        }
                        reservation.setSunbedIds(sunbedIds);
                        reservation.setReservationDate(selectedDate);

                        // Get the beach ID from the fragment arguments
                        String beachId = getArguments().getString("beach_id");

                        // Save the reservation using the ViewModel and pass the beach ID
                        mViewModel.saveReservation(beachId, reservation);
                        LiveData<Double> totalPrice = mViewModel.getTotalPrice();
                        showTotalPricePrompt(beachId, reservation, totalPrice);
                    } else {
                        Toast.makeText(getContext(), "Please select at least one sunbed", Toast.LENGTH_SHORT).show();
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

    private void showTotalPricePrompt(String beachId, Reservation reservation, LiveData<Double> totalPrice) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirm Reservation");

        // Create a flag to track if the observer has already been triggered
        final boolean[] observerTriggered = {false};

        // Observe the totalPrice LiveData
        totalPrice.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double totalPriceValue) {
                if (!observerTriggered[0]) {
                    observerTriggered[0] = true;

                    if (totalPriceValue != null) {
                        builder.setMessage(String.format(Locale.getDefault(), "The total price is %.2f. Do you want to proceed with the reservation?", totalPriceValue));
                    } else {
                        // Handle the case when the total price is null or not available
                        builder.setMessage("Total price not available. Do you want to proceed with the reservation?");
                    }

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Navigate to another fragment
                            FragmentManager fragmentManager = getParentFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.frame_layout, new ReservationsFragment()) // Replace with the appropriate fragment class
                                    .commit();

                            dialog.dismiss(); // Close the dialog
                        }
                    });

                    // TODO : fix the no button
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String reservationId = reservation.getReservationId();
                            if (reservationId != null) {
                                DatabaseReference reservationsRef = FirebaseDatabase.getInstance().getReference("Reservations");
                                reservationsRef.child(reservationId).setValue(null);
                            }
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

}
