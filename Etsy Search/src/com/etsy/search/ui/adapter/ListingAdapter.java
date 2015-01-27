
package com.etsy.search.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsy.search.R;
import com.etsy.search.data.Listing;
import com.etsy.search.utils.ImageCacheTask;

/***
 * Adapter class for the listing item
 */
public class ListingAdapter extends ArrayAdapter<Listing> {
    private static final String TAG = ListingAdapter.class.getSimpleName();
    private Context mContext;
    private List<Listing> mCategories;

    public ListingAdapter(Context context, List<Listing> objects) {
        super(context, R.layout.listing_row_item, objects);
        this.mContext = context;
        this.mCategories = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder; // to reference the child views for later actions
        Listing listing = mCategories.get(position);

        if (convertView == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
            convertView = mLayoutInflater.inflate(R.layout.listing_row_item,
                    parent, false);
            // cache view fields into the holder
            holder = new ViewHolder(convertView);
            // associate the holder with the view for later lookup
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.populateData(listing);
        return convertView;
    }

    /**
     * Class to store the view
     */
    private class ViewHolder {
        TextView mTitleView;
        TextView mPriceView;
        TextView mFavoriteView;
        TextView mQuantityView;
        ImageView mThumbImageView;

        public ViewHolder(View convertView) {
            mTitleView = (TextView) convertView
                    .findViewById(R.id.listing_title);
            mPriceView = (TextView) convertView
                    .findViewById(R.id.listing_price);
            mFavoriteView = (TextView) convertView
                    .findViewById(R.id.favorite_count);
            mQuantityView = (TextView) convertView
                    .findViewById(R.id.quantity);
            mThumbImageView = (ImageView) convertView.findViewById(R.id.thumbImage);
        }

        public void populateData(Listing listing) {
            mTitleView.setText(listing.getTitle());
            mPriceView.setText(listing.getPrice() + " " + listing.getCurrencyCode());
            mFavoriteView.setText("(" + listing.getNumFavorers() + ")");
            mQuantityView.setText(listing.getQuantity() + " "
                    + mContext.getString(R.string.available));

            if (mThumbImageView != null) {
                ImageCacheTask.getInstance(mContext).displayImage(
                        listing.getListingImage().getUrl170x135(),
                        mThumbImageView);
            }
        }
    }
}
