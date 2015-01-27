
package com.etsy.search.utils;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.etsy.search.connection.ConnectionHelper;
import com.etsy.search.data.Listing;

/**
 * Task responsible for fetching the listings.
 */
public class ListingTask extends AsyncTask<Void, ArrayList<Listing>, ArrayList<Listing>> {

    private String mSearchTerm;
    private boolean mImageRequired;
    private int mSortOrder;
    private int mOffset;
    private int mLimit;

    public ListingTask(String searchTerm, boolean image, int offset, int limit) {
        this(searchTerm, image, 0, offset, limit);
    }

    public ListingTask(String searchTerm, boolean image, int sortOrder, int offset, int limit) {
        this.mSearchTerm = searchTerm;
        this.mImageRequired = image;
        this.mSortOrder = sortOrder;
        this.mOffset = offset;
        this.mLimit = limit;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Listing> doInBackground(Void... arg0) {
        ArrayList<Listing> listings =
                ConnectionHelper.getInstance().getListings(mSearchTerm, mImageRequired, mSortOrder,
                        mOffset, mLimit);
        return listings;
    }

    @Override
    protected void onPostExecute(ArrayList<Listing> listings) {
        super.onPostExecute(listings);
    }

    public String getSearchTerm() {
        return mSearchTerm;
    }

    public void setSearchTerm(String mSearchTerm) {
        this.mSearchTerm = mSearchTerm;
    }

    public boolean isImageRequired() {
        return mImageRequired;
    }

    public void setImageRequired(boolean mImageRequired) {
        this.mImageRequired = mImageRequired;
    }

}
