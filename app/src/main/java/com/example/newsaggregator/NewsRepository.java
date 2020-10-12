package com.example.newsaggregator;

import android.app.Application;
import android.media.ImageReader;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NewsRepository {
    private NewsDao mNewsDao;
    private LiveData<List<NewsItem>> mAllNews;
    private final NewsRoomDatabase db;

    NewsRepository(Application application) {
        db = NewsRoomDatabase.getDatabase(application);
        mNewsDao = db.newsDao();
        mAllNews = mNewsDao.getAllNews();
    }

    LiveData<List<NewsItem>> getAllNews() {
        return mAllNews;
    }

    public void deleteAll() {
        new deleteAllNewsAsyncTask(mNewsDao).execute();
    }

    public void deleteNewsItem(NewsItem item) {
        new deleteNewsItemAsyncTask(mNewsDao).execute(item);
    }

    public LiveData<NewsItem> loadNewsItem(final int id) {
        return mNewsDao.loadNewsItem(id);
    }


    private static class deleteAllNewsAsyncTask extends AsyncTask<Void, Void, Void> {
        private NewsDao mAsyncTaskDao;
        deleteAllNewsAsyncTask(NewsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteNewsItemAsyncTask extends AsyncTask<NewsItem, Void, Void> {
        private NewsDao mAsyncTaskDao;
        deleteNewsItemAsyncTask(NewsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(NewsItem... items) {
            mAsyncTaskDao.deleteNewsItem(items[0]);
            return null;
        }
    }
}
