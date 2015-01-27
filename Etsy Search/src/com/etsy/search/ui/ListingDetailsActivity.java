
package com.etsy.search.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.etsy.search.R;

/***
 * Class to show the detailed item. Launched the webview to show the item
 */
public class ListingDetailsActivity extends ActionBarActivity {

    private static final String TAG = ListingDetailsActivity.class.getSimpleName();
    private WebView mEtsyDetailsView;
    private Toolbar mToolbar;
    private ProgressBar mProgressBarLoadMore;
    private TextView mPlaceHolderView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra("URL");
        url = url.substring(0, url.indexOf("?"));
        setContentView(R.layout.details);
        mEtsyDetailsView = (WebView) findViewById(R.id.detailsPage);
        mProgressBarLoadMore = (ProgressBar) findViewById(R.id.progress_bar_load_more);
        mPlaceHolderView = (TextView) findViewById(R.id.placeholder);
        mProgressBarLoadMore.setVisibility(View.VISIBLE);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setLogo(R.drawable.ic_launcher);
        mToolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEtsyDetailsView.getSettings().setBuiltInZoomControls(false);
        mEtsyDetailsView.getSettings().setJavaScriptEnabled(true);
        mEtsyDetailsView.loadUrl(url);
        mEtsyDetailsView.scrollBy(0, 710);
        mEtsyDetailsView.setWebViewClient(new WebViewClient());
        mEtsyDetailsView.setVisibility(View.VISIBLE);
        mProgressBarLoadMore.setVisibility(View.GONE);
        mPlaceHolderView.setVisibility(View.GONE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
