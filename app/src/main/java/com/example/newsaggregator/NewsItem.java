package com.example.newsaggregator;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "news_table")
public class NewsItem {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NotNull
    @ColumnInfo(name = "title")
    private String mTitle;

    @NotNull
    @ColumnInfo(name = "description")
    private String mDescription;

    @NotNull
    @ColumnInfo(name = "url")
    private String mUrl;

    @NotNull
    @ColumnInfo(name = "publish_time")
    private String mTime;

    @NotNull
    @ColumnInfo(name = "content")
    private String mContent;

    public NewsItem(@NotNull String mTitle, @NotNull String mDescription,
                    @NotNull String mUrl, @NotNull String mTime, @NotNull String mContent) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mUrl = mUrl;
        this.mTime = mTime;
        this.mContent = mContent;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getContent() {
        return mContent;
    }

    public String getTime() {
        return mTime;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setId(int id) {
        this.id = id;
    }
}
