package com.example.newsaggregator;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SearchFragment extends Fragment {
    static final String SEARCH_STRING = SearchFragment.class.getName() + "_SEARCH_STRING";
    private NewsViewModel mNewsViewModel;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment getInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        //Every time search fragment start the database clears
        mNewsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        Button button = view.findViewById(R.id.searchButton);
        final EditText searchInputField = view.findViewById(R.id.searchEditText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchString = searchInputField.getText().toString();
                if (!searchString.isEmpty()) {
                    mNewsViewModel.deleteAll();
                    mNewsViewModel.updateAll(searchString);
                    startListFragment();
                }
            }
        });

        return view;
    }

    private void startListFragment() {
        Context context = getActivity();
        if (context instanceof MainActivity) {
            MainActivity activity = (MainActivity) context;
            activity.startNewsListFragment();
        }
    }
}
