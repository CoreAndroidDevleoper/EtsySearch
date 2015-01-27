
package com.etsy.search.ui;

import android.widget.AbsListView;

/**
 * Implementation of Endless ScrollListner
 */
public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {
    private int mBufferItemCount = 10;
    private int mCurrentPage = 0;
    private int mItemCount = 0;
    private boolean mIsListLoading = true;

    public EndlessScrollListener(int bufferItemCount) {
        this.mBufferItemCount = bufferItemCount;
    }

    /***
     * Called when the end of the list has been reached
     * 
     * @param page
     * @param totalItemsCount
     */
    public abstract void onEndReached(int page, int totalItemsCount);

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // Do Nothing
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
            int totalItemCount)
    {
        if (totalItemCount < mItemCount) {
            this.mItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.mIsListLoading = true;
            }
        }

        if (mIsListLoading && (totalItemCount > mItemCount)) {
            mIsListLoading = false;
            mItemCount = totalItemCount;
            mCurrentPage++;
        }

        if (!mIsListLoading
                && (totalItemCount - visibleItemCount) <= (firstVisibleItem + mBufferItemCount)) {
            onEndReached(mCurrentPage + 1, totalItemCount);
            mIsListLoading = true;
        }
    }
}
