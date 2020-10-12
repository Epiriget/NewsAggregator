package com.example.newsaggregator;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {NewsItem.class}, version = 1, exportSchema = false)
public abstract class NewsRoomDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();

    private static NewsRoomDatabase INSTANCE;

    public static NewsRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (NewsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NewsRoomDatabase.class, "news_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final NewsDao mDao;

        PopulateDbAsync(NewsRoomDatabase db) {
            this.mDao = db.newsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(mDao.getAnyNewsItem().length < 1) {
                NewsDownloadAsyncTask task = new NewsDownloadAsyncTask();
                List<NewsItem> items = task.getNewsList();
//                List<NewsItem> items = new ArrayList<>();
//                items.add(new NewsItem("Title", "Description", "someurl", "time", "content"));
                for (NewsItem item:items) {
                    mDao.insert(item);
                }
            }
            return null;
        }
    }
}
