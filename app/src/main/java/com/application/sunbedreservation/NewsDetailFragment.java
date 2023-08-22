package com.application.sunbedreservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

public class NewsDetailFragment extends Fragment {

    private ImageView photoImageView;
    private TextView titleTextView;
    private TextView descriptionTextView;

    public NewsDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_detail, container, false);

        photoImageView = rootView.findViewById(R.id.detailPhotoImageView);
        titleTextView = rootView.findViewById(R.id.detailTitleTextView);
        descriptionTextView = rootView.findViewById(R.id.detailDescriptionTextView);

        // Retrieve data passed from the news list fragment and set the values
        Bundle arguments = getArguments();
        if (arguments != null) {
            String imageUrl = arguments.getString("image_url");
            String title = arguments.getString("title");
            String description = arguments.getString("description");

            Picasso.get().load(imageUrl).into(photoImageView);
            titleTextView.setText(title);
            descriptionTextView.setText(description);
        }

        // Go back to the news list
        Button backToNewsListButton = rootView.findViewById(R.id.backToNewsListButton);
        backToNewsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsFragment newsListFragment = new NewsFragment();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, newsListFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }
}

