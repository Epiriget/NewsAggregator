package com.example.newsaggregator;

import android.app.Application;
import android.media.ImageReader;
import android.os.AsyncTask;
import android.os.SystemClock;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

    public void updateAll(final String searchString) {
        final Completable serverDownloadCompletable =
                Completable.create(emitter -> {
                    NewsDownloadAsyncTask downloadTask = new NewsDownloadAsyncTask();
                    downloadTask.getNewsList(searchString).forEach(mNewsDao::insert);
                    emitter.onComplete();
                });

        Disposable subscribe = serverDownloadCompletable
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    private void fetchNewsByStringCompletable(){}


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
