package com.example.newsaggregator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends Fragment {
    private NewsViewModel mNewsViewModel;

    public NewsListFragment() {
        // Required empty public constructor
    }

    //Todo: uncomment in case of using arguments (search for word/number occurrence)
    public static NewsListFragment getInstance() {
        NewsListFragment fragment = new NewsListFragment();
//        Bundle arguments = new Bundle();
//        arguments.putInt(SongUtils.SONG_ID_KEY, selectedSong);
//        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycleView);
        final NewsListAdapter adapter = new NewsListAdapter();
        recyclerView.setAdapter(adapter);
        DividerItemDecoration divider = new DividerItemDecoration
                (rootView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        mNewsViewModel = ViewModelProviders.of(this)
                .get(NewsViewModel.class);

        mNewsViewModel.getAllNews().observe(getActivity(), list -> adapter.setNews(list));
        return rootView;
    }
}
