package com.application.sunbedreservation;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SunbedReservationViewModel extends ViewModel {
    private MutableLiveData<Map<Integer, List<Sunbed>>> sunbedRows = new MutableLiveData<>();
    private MutableLiveData<String> currentUserId = new MutableLiveData<>();
    private MutableLiveData<Double> totalPrice = new MutableLiveData<>();
    private MutableLiveData<String> titleLiveData = new MutableLiveData<>();
    private MutableLiveData<String> subTitleLiveData = new MutableLiveData<>();


    public LiveData<Map<Integer, List<Sunbed>>> getSunbedRows() {
        return sunbedRows;
    }
    public LiveData<String> getUserId() {
        return currentUserId;
    }
    public LiveData<Double> getTotalPrice() {
        return totalPrice;
    }
    public LiveData<String> getTitleLiveData() {
        return titleLiveData;
    }

    public LiveData<String> getSubTitleLiveData() {
        return subTitleLiveData;
    }


    public void updateTotalPrice(double price) {
        totalPrice.setValue(price);
    }

    public void fetchBeachData(String beachId) {
        DatabaseReference beachRef = FirebaseDatabase.getInstance().getReference("Beaches").child(beachId);
        beachRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String title = dataSnapshot.child("title").getValue(String.class);
                    String subTitle = dataSnapshot.child("subTitle").getValue(String.class);

                    titleLiveData.setValue(title);
                    subTitleLiveData.setValue(subTitle);

                    Log.d("BeachData", "Title: " + title);
                    Log.d("BeachData", "SubTitle: " + subTitle);
                } else {
                    // Handle the case where the data doesn't exist
                    Log.d("BeachData", "Data not found for beachId: " + beachId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the case where the data retrieval was canceled or failed
                Log.e("Failed to get data", databaseError.getMessage());
            }
        });
    }

    public void fetchSunbeds(String sunbedId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Beaches").child(sunbedId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<Integer, List<Sunbed>> sunbedMap = new HashMap<>();

                for (DataSnapshot childSnapshot : snapshot.child("sunbeds").getChildren()) {
                    Sunbed sunbed = childSnapshot.getValue(Sunbed.class);
                    if (sunbed != null) {
                        int row = sunbed.getRow();
                        if (!sunbedMap.containsKey(row)) {
                            sunbedMap.put(row, new ArrayList<>());
                        }
                        sunbedMap.get(row).add(sunbed);
                    }
                }
                sunbedRows.setValue(sunbedMap);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Failed to get data from Firebase", error.getMessage());
            }
        });
    }

    private String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        } else {
            return null;
        }
    }

    public void saveReservation(String beachId, Reservation reservation) {
        DatabaseReference reservationsRef = FirebaseDatabase.getInstance().getReference("Reservations");
        DatabaseReference beachRef = FirebaseDatabase.getInstance().getReference("Beaches").child(beachId);

        // Generate a new reservation ID
        String reservationId = reservationsRef.push().getKey();

        CompletableFuture<String> future = getTitleFromFirebase(beachId);
        future.thenAccept(title -> {
            if (title != null) {
                System.out.println("Title: " + title);
                reservation.setBeachTitle(title); // Set the beach title in the reservation object
                // Add the beach ID to the reservation data
                reservation.setBeachId(beachId);
                reservation.setUserId(getCurrentUserId());

                String currentMonth = getCurrentMonth();

                beachRef.child("BeachPrices").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Double row1price = snapshot.child("row1" + currentMonth).getValue(Double.class);
                        Double row2price = snapshot.child("row2" + currentMonth).getValue(Double.class);

                        Log.i("current month", currentMonth);
                        Log.i("row1price", String.valueOf(row1price));
                        Log.i("row2price", String.valueOf(row2price));

                        AtomicReference<Double> totalCost = new AtomicReference<>(0.0);
                        List<String> sunbedIds = reservation.getSunbedIds();
                        AtomicInteger sunbedCount = new AtomicInteger(sunbedIds.size());

                        for (String sunbedId : sunbedIds) {
                            DatabaseReference sunbedRef = beachRef.child("sunbeds").child(sunbedId);
                            sunbedRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Integer row = snapshot.child("row").getValue(Integer.class);
                                    if (row != null) {
                                        if (row.equals(1)) {
                                            totalCost.updateAndGet(v -> v + row1price);
                                        } else if (row.equals(2)) {
                                            totalCost.updateAndGet(v -> v + row2price);
                                        }
                                    }
                                    // Decrease the sunbed count
                                    int updatedCount = sunbedCount.decrementAndGet();

                                    // Check if all sunbeds have been processed
                                    if (updatedCount == 0) {
                                        reservation.setTotalCost(totalCost.get());
                                        updateTotalPrice(totalCost.get());
                                        Log.i("Total Cost", String.valueOf(reservation.getTotalCost()));

                                        // Save the reservation to the database
                                        reservationsRef.child(reservationId).setValue(reservation)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        // Reservation saved successfully
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Failed to save reservation
                                                    }
                                                });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // Database read operation canceled
                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Failed getting prices from the database", String.valueOf(error));
                    }
                });

                // Save the reservation to the "reservations" node
                reservationsRef.child(reservationId).setValue(reservation)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Reservation saved successfully

                                // Update the reserved dates for each selected sunbed
                                List<String> sunbedIds = reservation.getSunbedIds();
                                String reservationDate = reservation.getReservationDate();

                                for (String sunbedId : sunbedIds) {
                                    DatabaseReference sunbedRef = beachRef.child("sunbeds").child(sunbedId);

                                    sunbedRef.child("reservedDates").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            List<String> reservedDates = new ArrayList<>();
                                            if (snapshot.exists()) {
                                                // Get the current reserved dates
                                                reservedDates = snapshot.getValue(new GenericTypeIndicator<List<String>>() {});
                                            }

                                            // Add the reservation date to the reserved dates
                                            reservedDates.add(reservationDate);

                                            // Update the reserved dates for the sunbed
                                            sunbedRef.child("reservedDates").setValue(reservedDates)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            // Reserved dates updated successfully
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            // Failed to update reserved dates
                                                        }
                                                    });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            // Database read operation canceled
                                        }
                                    });
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to save reservation
                            }
                        });
            } else {
                System.out.println("Beach not found.");
            }
        }).exceptionally(e -> {
            System.out.println("Error: " + e.getMessage());
            return null;
        });
    }


    public void fetchReservedDatesByBeach() {
        DatabaseReference reservationsRef = FirebaseDatabase.getInstance().getReference("Reservations");
        reservationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, List<String>> reservedDatesByBeach = new HashMap<>();

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Reservation reservation = childSnapshot.getValue(Reservation.class);
                    if (reservation != null) {
                        String beachId = reservation.getBeachId();
                        String reservationDate = reservation.getReservationDate();

                        if (!reservedDatesByBeach.containsKey(beachId)) {
                            reservedDatesByBeach.put(beachId, new ArrayList<>());
                        }
                        reservedDatesByBeach.get(beachId).add(reservationDate);
                    }
                }

                // Do something with the reserved dates ordered by beach
                // You can update a LiveData object or perform any other desired operation
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Failed to get data from Firebase", error.getMessage());
            }
        });
    }

    public String getCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(DateTimeFormatter.ofPattern("MMMM"));
    };

    public static CompletableFuture<String> getTitleFromFirebase(String beachId) {
        CompletableFuture<String> future = new CompletableFuture<>();
        DatabaseReference beachRef = FirebaseDatabase.getInstance().getReference("Beaches").child(beachId);
        beachRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String title = dataSnapshot.child("title").getValue(String.class);
                    future.complete(title);
                } else {
                    future.complete(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future;
    }
}

