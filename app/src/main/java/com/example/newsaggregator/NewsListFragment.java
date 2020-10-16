package com.example.newsaggregator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends Fragment {
    private NewsViewModel mNewsViewModel;

    public NewsListFragment() {
        // Required empty public constructor
    }

    public static NewsListFragment getInstance(final String searchString) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle arguments = new Bundle();
        arguments.putString(SearchFragment.SEARCH_STRING, searchString);
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);

        final NewsListAdapter adapter = new NewsListAdapter();
        RecyclerView recyclerView = rootView.findViewById(R.id.recycleView);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration divider = new DividerItemDecoration
                (rootView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        NewsItem newsItem = adapter.getNewsItemAtPosition(position);
                        mNewsViewModel.deleteNewsItem(newsItem);
                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        mNewsViewModel = ViewModelProviders.of(this)
                .get(NewsViewModel.class);

        mNewsViewModel.getAllNews().observe(getActivity(),
                list -> {
                    if (list.size() > 0) {
                        adapter.setNews(list);
                    }
                });
        return rootView;
    }
}
