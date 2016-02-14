package com.example.k.hw2nytimes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsItemAdapter extends
        RecyclerView.Adapter<NewsItemAdapter.ViewHolder> {

    // ... constructor and member variables
    // Store a member variable for the contacts
    private List<NewsItem> nItems;
    private Context context;

    // Pass in the contact array into the constructor
    public NewsItemAdapter(List<NewsItem> items, Context context) {
        nItems = items;
        this.context = context;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public NewsItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.news_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView, context);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(NewsItemAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final NewsItem item = nItems.get(position);

        // Set item views based on the data model
        TextView headLine = viewHolder.headLine;
        headLine.setText(item.getHeadLine());

        ImageView thumbnail = viewHolder.thumbnail;
        Glide.with(context).load("http://nytimes.com/" + item.getImageUrl()).into(thumbnail);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, BrowserActivity.class);
                intent.putExtra("url", item.getNewsUrl());
                context.startActivity(intent);

            }
        });

    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return nItems.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView headLine;
        public ImageView thumbnail;
        public Context context;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, Context context) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            this.context = context;

            headLine = (TextView) itemView.findViewById(R.id.headline);
            thumbnail = (ImageView) itemView.findViewById(R.id.newsThumbnail);
        }
    }
}