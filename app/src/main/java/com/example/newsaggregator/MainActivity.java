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

        startNewsListFragment();
    }

    public void startNewsItemFragment(NewsItemFragment newsItemFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.news_list_container, newsItemFragment)
                .addToBackStack(null)
                .commit();
    }

    private void startNewsListFragment() {
        NewsListFragment newsListFragment = NewsListFragment.getInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.news_list_container, newsListFragment)
                .addToBackStack(null)
                .commit();
    }



}
