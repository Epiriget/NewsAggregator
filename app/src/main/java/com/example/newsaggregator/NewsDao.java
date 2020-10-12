package com.example.newsaggregator;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewsItem item);

    @Query("DELETE FROM news_table")
    void deleteAll();

    @Query("SELECT * FROM news_table")
    LiveData<List<NewsItem>> getAllNews();

    @Query("SELECT * FROM news_table WHERE id = :id")
    LiveData<NewsItem> loadNewsItem(int id);

    @Query("SELECT * FROM news_table LIMIT 1")
    NewsItem[] getAnyNewsItem();

    @Delete
    void deleteNewsItem(NewsItem item);

    @Update
    void update(NewsItem... item);


}
