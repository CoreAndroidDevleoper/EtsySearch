
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etsy.search.R;
import com.etsy.search.data.Listing;
import com.etsy.search.ui.adapter.ListingAdapter;
import com.etsy.search.utils.ListingTask;

/***
 * Launcher class where the authentication check should be done for the user. Assuming in this case
 * that user has authenticated.
 */
public class EtsySearchLauncher extends ActionBarActivity {
    private static final String TAG = EtsySearchLauncher.class.getSimpleName();

    // view related
    private ListView mListView;
    private TextView mEtsySearchSplashText;
    private ProgressBar mProgressBar;
    private ProgressBar mProgressBarLoadMore;
    private ListingAdapter mAdapter;
    private RelativeLayout mListingLayout;
    private TextView mEmptyView;
    private Toolbar mToolbar;

    // data
    private String mSearchTerm = "";
    private int mLimit = 25;
    private int mOffset = 0;
    private int mPosition = 0;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.splash);

        // get the view
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setLogo(R.drawable.ic_launcher);
        mListView = (ListView) findViewById(R.id.listing_list);
        mEmptyView = (TextView) findViewById(R.id.empty);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mEtsySearchSplashText = (TextView) findViewById(R.id.etsy_search_text);
        mListingLayout = (RelativeLayout) findViewById(R.id.etsy_search_layout);
        mProgressBarLoadMore = (ProgressBar) findViewById(R.id.progress_bar_load_more);

        // set the list view
        mAdapter = new ListingAdapter(this, new ArrayList<Listing>());
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mListingClickListner);
        mListView.setOnScrollListener(mScrollListener);
        mListView.setEmptyView(mEmptyView);
        mListView.setFastScrollEnabled(false);

        // set up the toolbar
        setTitle(getString(R.string.app_name));
        setTitleColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getString(R.string.tsy_search));
        mToolbar.setSubtitle("#Trending");
        mToolbar.setSubtitleTextColor(Color.BLUE);
        mToolbar.setTitleTextColor(Color.WHITE);

        // start the first task
        mSearchTerm = "trending";
        new TrendingResults(mSearchTerm, true, mPosition, mOffset, mLimit).execute();
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
        // Use the refelection to change the text color of edit text
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
                mListingLayout.setVisibility(View.VISIBLE);
                mEtsySearchSplashText.setVisibility(View.GONE);
                mProgressBarLoadMore.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
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
        mListingLayout.setVisibility(View.VISIBLE);
        mEtsySearchSplashText.setVisibility(View.GONE);
        if (itemSize > 0) {
            mListView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setText(getString(R.string.no_results));
            mEmptyView.setVisibility(View.VISIBLE);
        }
        mProgressBar.setVisibility(View.GONE);
        mProgressBarLoadMore.setVisibility(View.GONE);
    }

    private OnItemClickListener mListingClickListner = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {

            Intent intent = new Intent();
            intent.putExtra("URL", mAdapter.getItem(position).getUrl());
            intent.setClass(EtsySearchLauncher.this, ListingDetailsActivity.class);
            startActivity(intent);
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
