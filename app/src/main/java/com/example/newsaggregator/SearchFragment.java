package com.example.newsaggregator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.operators.single.SingleDoAfterTerminate;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
        mNewsViewModel.deleteAll();

        Button button = view.findViewById(R.id.searchButton);
        final EditText searchInputField = view.findViewById(R.id.searchEditText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchString = searchInputField.getText().toString();
                startFragment(searchString);
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
