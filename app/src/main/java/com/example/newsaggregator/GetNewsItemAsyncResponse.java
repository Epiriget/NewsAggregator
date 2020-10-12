package com.example.newsaggregator;

import androidx.lifecycle.LiveData;

public interface GetNewsItemAsyncResponse {
    void getItem(LiveData<NewsItem> item);
}