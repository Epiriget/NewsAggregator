package com.example.newsaggregator;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    static final String SEARCH_STRING = SearchFragment.class.getName() + "_SEARCH_STRING";

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
        Button button = view.findViewById(R.id.searchButton);
        EditText searchInputField = view.findViewById(R.id.searchEditText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(searchInputField.getText().toString());
            }
        });

        return view;
    }

    private void startFragment(final String searchString) {
        if(!searchString.isEmpty()) {
            Context context = getActivity();
            if (context instanceof MainActivity) {
                MainActivity activity = (MainActivity) context;
                activity.startNewsListFragment(searchString);
            }
        }
    }
}
