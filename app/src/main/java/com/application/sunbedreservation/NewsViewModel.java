package com.application.sunbedreservation;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends ViewModel {
    private MutableLiveData<List<News>> newsLiveData;
    private DatabaseReference newsRef;

    public LiveData<List<News>> getNewsLiveData() {
        if (newsLiveData == null) {
            newsLiveData = new MutableLiveData<>();
            loadNewsData();
        }
        return newsLiveData;
    }

    private void loadNewsData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        newsRef = database.getReference("News");

        newsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<News> newsList = new ArrayList<>();

                for (DataSnapshot newsSnapshot : snapshot.getChildren()) {
                    // Parse the News object from the DataSnapshot
                    News news = newsSnapshot.getValue(News.class);
                    newsList.add(news);
                }

                // Update the LiveData
                newsLiveData.setValue(newsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database Error", String.valueOf(error));
            }
        });
    }

}

