package com.example.newsaggregator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.text.Annotation;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsItemFragment extends Fragment {
    public static final String PRODUCT_ID_KEY = "NewsItemFragment.product_id_key";
    private NewsViewModel mNewsViewModel;
    private TextView mNewsItemDate;
    private TextView mNewsItemTitle;
    private TextView mNewsItemText;
    private TextView mNewsItemSrc;
    public NewsItemFragment() {
        // Required empty public constructor
    }

    public static NewsItemFragment getInstance(final int newsItemId) {
        NewsItemFragment fragment = new NewsItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PRODUCT_ID_KEY, newsItemId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_item, container, false);
        mNewsItemDate = view.findViewById(R.id.newsItemDate);
        mNewsItemSrc = view.findViewById(R.id.newsItemSrc);
        mNewsItemText = view.findViewById(R.id.newsItemText);
        mNewsItemTitle = view.findViewById(R.id.newsItemTitle);

        Bundle arguments = getArguments();
        int newsItemId = 0;
        if(arguments != null) {
            newsItemId = arguments.getInt(PRODUCT_ID_KEY, 0);
        }
        mNewsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        mNewsViewModel.loadNewsItem(newsItemId).observe(getActivity(), item -> {
            updateViewContent(item);
        });

        return view;
    }

    private void updateViewContent(NewsItem item) {
        mNewsItemTitle.setText(item.getTitle());
        mNewsItemText.setText(item.getContent());
        mNewsItemDate.setText(item.getTime());

        String linkString = getString(R.string.link_src_label);
        SpannableString string = new SpannableString(linkString);
        string.setSpan(new URLSpan(item.getUrl()), linkString.indexOf("source"),
                linkString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mNewsItemSrc.setText(string);
        mNewsItemSrc.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
