package com.example.newsaggregator;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class NewsDownloadAsyncTask {

    List<NewsItem> getNewsList(final String searchString) {
        String query = "https://newsapi.org/v2/top-headlines?q="
                .concat(searchString)
                .concat("&language=en&apiKey=0c681df656cb4216a48f9abf3c8dc6c2");
        Log.d("NewsDownloadAsyncTask query:", query);
        String stringResult = getJsonFromQuery(query);
        List<NewsItem> newsList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(stringResult);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject singleNews = jsonArray.getJSONObject(i);
                String title = singleNews.getString("title");
                String description = singleNews.getString("description");
                String time = singleNews.getString("publishedAt");
                String url = singleNews.getString("url");
                String content = singleNews.getString("content");
                if(!title.equals("null") && !content.equals("null")) {
                    newsList.add(new NewsItem(title, description, url, time, content));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsList;
    }

    private String getJsonFromQuery(String query) {
        HttpsURLConnection connection = null;
        BufferedReader reader;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL requestURL = new URL(query);

            connection = (HttpsURLConnection) requestURL.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String tmp;
            while ((tmp = reader.readLine()) != null) {
                stringBuilder.append(tmp).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return stringBuilder.toString();
    }

}