package com.example.newsaggregator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NewsViewModel mNewsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsViewModel = ViewModelProviders.of(this)
                .get(NewsViewModel.class);

        BottomNavigationView bottomView = findViewById(R.id.bottomNavigation);
        bottomView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_search_page:
                    startSearchFragment();
                    return true;
                case R.id.menu_list_page:
                    startNewsListFragment();
                    return true;
                case R.id.menu_favourites_page:
                    startNewsListFragment();
                    return true;
                default:
                    return false;
            }
        });
        startSearchFragment();
    }

    private void startSearchFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.news_list_container, SearchFragment.getInstance())
                .commit();
    }

    public void startNewsItemFragment(final int newsItemId) {
        NewsItemFragment fragment = NewsItemFragment.getInstance(newsItemId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.news_list_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void startNewsListFragment() {
        NewsListFragment newsListFragment = NewsListFragment.getInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.news_list_container, newsListFragment)
                .addToBackStack(null)
                .commit();
    }



}
