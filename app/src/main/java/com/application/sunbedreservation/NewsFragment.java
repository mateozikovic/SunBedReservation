package com.application.sunbedreservation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private NewsViewModel newsViewModel; // Initialize your ViewModel

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        newsRecyclerView = view.findViewById(R.id.newsRecyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the newsAdapter
        newsAdapter = new NewsAdapter(requireContext(), new ArrayList<>());
        newsRecyclerView.setAdapter(newsAdapter);

        // Set up the RecyclerView's layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        newsRecyclerView.setLayoutManager(layoutManager);

        // Fetch news data and update the adapter using ViewModel
        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        newsViewModel.getNewsLiveData().observe(getViewLifecycleOwner(), new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> newsList) {
                newsAdapter.setData(newsList);
            }
        });
    }
}

