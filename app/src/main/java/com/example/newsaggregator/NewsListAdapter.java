package com.example.newsaggregator;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {
    List<NewsItem> mNews;
    private Context mContext;
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recycleview_item, parent, false);
        final NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        if(mNews != null) {
             NewsItem current = mNews.get(position);
             holder.bind(current);
        }
        holder.itemView.setOnClickListener(v -> {
            NewsItem item = mNews.get(position);
            startFragment(item.getId());
        });
    }

    private void startFragment(final int newsItemId) {
        if(mContext instanceof MainActivity) {
            MainActivity activity = (MainActivity) mContext;
            activity.startNewsItemFragment(newsItemId);
        }
    }

    public NewsItem getNewsItemAtPosition(int position) {
        return mNews.get(position);
    }

    @Override
    public int getItemCount() {
        return (mNews != null)? mNews.size(): 0;
    }

    public void setNews(List<NewsItem> news) {
        mNews = news;
        notifyDataSetChanged();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView newsTitleTextView;
        private TextView newsContentTextView;
        private NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitleTextView = itemView.findViewById(R.id.itemHeader);
            newsContentTextView = itemView.findViewById(R.id.itemContent);
        }

        void bind(NewsItem item) {
            newsTitleTextView.setText(item.getTitle());
            newsContentTextView.setText(item.getContent());
        }


    }
}
