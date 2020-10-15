package com.example.newsaggregator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NewsViewModel mNewsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startSearchFragment();
    }

    private void startSearchFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.news_list_container, SearchFragment.getInstance())
                .addToBackStack(null)
                .commit();
    }

    public void startNewsItemFragment(final int newsItemId) {
        NewsItemFragment fragment = NewsItemFragment.getInstance(newsItemId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.news_list_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void startNewsListFragment(String searchString) {
        NewsListFragment newsListFragment = NewsListFragment.getInstance(searchString);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.news_list_container, newsListFragment)
                .addToBackStack(null)
                .commit();
    }



}
