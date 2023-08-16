package com.application.sunbedreservation;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> newsList;
    private Context context;

    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.bind(news);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setData(List<News> newData) {
        newsList.clear();
        newsList.addAll(newData);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private ImageView photoImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
        }

        public void bind(News news) {
            titleTextView.setText(news.getTitle());
            Picasso.get().load(news.getImageURL()).placeholder(R.drawable.default_news_image).into(photoImageView);

            // Set a click listener for the news item
            itemView.setOnClickListener(v -> {
                // Handle click event here, you can navigate to the detail fragment
                // or perform any other action based on the clicked news item.
            });
        }
    }
}
