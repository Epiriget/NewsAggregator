package com.example.newsaggregator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private NewsRepository mRepository;
    private LiveData<List<NewsItem>> mAllNews;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NewsRepository(application);
        mAllNews = mRepository.getAllNews();
    }

    public LiveData<List<NewsItem>> getAllNews() {
        return mAllNews;
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public LiveData<NewsItem> loadNewsItem(final int id) {
        return mRepository.loadNewsItem(id);
    }

    public void deleteNewsItem(NewsItem item) {
        mRepository.deleteNewsItem(item);
    }

}
