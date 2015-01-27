
package com.etsy.search.ui;

import java.util.ArrayList;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.etsy.search.R;
import com.etsy.search.data.Listing;
import com.etsy.search.ui.adapter.ListingAdapter;
import com.etsy.search.utils.ListingTask;

public class SearchActivity extends ActionBarActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    // view related
    private ListView mListView;
    private ProgressBar mProgressBarLoadMore;
    private TextView mEmptyView;
    private ListingAdapter mAdapter;
    private Toolbar mToolbar;
    private Spinner mSpinner;

    // data
    private int mLimit = 25;
    private int mOffset = 0;
    private int mPosition = 0;
    private String mSearchTerm = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // get the view objects
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (ListView) findViewById(R.id.listing_list);
        mProgressBarLoadMore = (ProgressBar) findViewById(R.id.progress_bar_load_more);
        mEmptyView = (TextView) findViewById(R.id.empty);
        mSpinner = (Spinner) findViewById(R.id.spinner);

        // set up list view
        mAdapter = new ListingAdapter(this, new ArrayList<Listing>());
        mListView.setOnItemClickListener(mListingClickListner);
        mListView.setOnScrollListener(mScrollListener);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(mEmptyView);
        mListView.setFastScrollEnabled(false);

        // set up spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mToolbar.getContext(),
                R.array.sort_order, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(mItemSelectedListener);

        // set up toolbar
        setTitle(getString(R.string.app_name));
        setTitleColor(Color.WHITE);
        mToolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(mToolbar);
        mToolbar.setSubtitle(mSearchTerm);
        mToolbar.setSubtitleTextColor(Color.BLUE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mEmptyView.setText(getString(R.string.searching));
            mSearchTerm = intent.getStringExtra(SearchManager.QUERY);
            mProgressBarLoadMore.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            mAdapter.clear();
            new TrendingResults(mSearchTerm, true, mPosition, mOffset, mLimit).execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setTextColor(Color.WHITE);
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setHintTextColor(Color.WHITE);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    class TrendingResults extends ListingTask {

        public TrendingResults(String searchTerm, boolean image, int sortOrder, int offset,
                int limit) {
            super(searchTerm, image, sortOrder, offset, limit);
        }

        @Override
        protected void onPostExecute(ArrayList<Listing> listings) {
            super.onPostExecute(listings);
            if (listings == null || mAdapter == null) {
                mEmptyView.setText(getString(R.string.no_data));
                mProgressBarLoadMore.setVisibility(View.GONE);
                mEmptyView.setVisibility(View.VISIBLE);
                return;
            }
            mOffset = mOffset + listings.size();
            mAdapter.addAll(listings);
            mAdapter.notifyDataSetChanged();
            updateView(listings.size());
        }
    }

    private void updateView(int itemSize) {
        if (itemSize > 0) {
            mListView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setText(getString(R.string.no_results));
            mEmptyView.setVisibility(View.VISIBLE);
        }
        mProgressBarLoadMore.setVisibility(View.GONE);
    }

    private OnItemClickListener mListingClickListner = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

            Intent intent = new Intent();
            intent.putExtra("URL", mAdapter.getItem(position).getUrl());
            intent.setClass(SearchActivity.this, ListingDetailsActivity.class);
            startActivity(intent);
        }

    };

    private OnItemSelectedListener mItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View view,
                int pos, long id) {
            mOffset = 0;
            mAdapter.clear();
            mListView.setVisibility(View.GONE);
            mProgressBarLoadMore.setVisibility(View.VISIBLE);
            mPosition = pos;
            new TrendingResults(mSearchTerm, true, mPosition, mOffset, mLimit).execute();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    };

    private EndlessScrollListener mScrollListener = new EndlessScrollListener(5) {
        @Override
        public void onEndReached(int page, int totalItemsCount) {
            new TrendingResults(mSearchTerm, true, mPosition, mOffset, mLimit).execute();
            mProgressBarLoadMore.setVisibility(View.VISIBLE);
        }
    };

}
