
package com.etsy.search.connection.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.etsy.search.EtsyLogs;
import com.etsy.search.data.Listing;
import com.etsy.search.data.ListingImage;
import com.etsy.search.data.ListingTag;
import com.etsy.search.data.RequestHelper;

/**
 * Class to parse the Listings
 */
public class ListingParser {

    private static final String TAG = ListingParser.class.getSimpleName();

    public ListingParser() {

    }

    public ArrayList<Listing> getListing(String response) {
        if (response == null) {
            EtsyLogs.e(TAG, "response is null");
            return null;
        }
        return parse(response);

    }

    /***
     * method to parse response <b>(NOTE: Only methods that are needed are only parsed)</b>
     * 
     * @param response
     * @return
     */
    private ArrayList<Listing> parse(String response) {
        if (response == null) {
            EtsyLogs.e(TAG, "response is null, hence returing the null");
            return null;
        }
        ArrayList<Listing> resultsList = new ArrayList<>();
        try {
            JSONObject jsonObj = new JSONObject(response);

            // Getting JSON Array node
            JSONArray listings = jsonObj.getJSONArray(RequestHelper.TAG_RESULTS);
            int listingCount = listings.length();
            // looping through All listings
            for (int i = 0; i < listingCount; i++) {
                JSONObject listingObject = listings.getJSONObject(i);

                Listing listing = new Listing();

                // title
                if (listingObject.getString(ListingTag.title) != null) {
                    listing.setTitle(listingObject.getString(ListingTag.title));
                }

                // url
                if (listingObject.getString(ListingTag.url) != null) {
                    listing.setUrl(listingObject.getString(ListingTag.url));
                }

                // price
                if (listingObject.getString(ListingTag.price) != null) {
                    listing.setPrice(listingObject.getString(ListingTag.price));
                }

                // num favoraties
                if (listingObject.getString(ListingTag.num_favorers) != null) {
                    listing.setNumFavorers(listingObject.getString(ListingTag.num_favorers));
                }

                // currency code
                if (listingObject.getString(ListingTag.currency_code) != null) {
                    listing.setCurrencyCode(listingObject.getString(ListingTag.currency_code));
                }

                // quantity
                if (listingObject.getString(ListingTag.quantity) != null) {
                    listing.setQuantity(listingObject.getString(ListingTag.quantity));
                }
                
                // image
                JSONObject imageListing = listingObject.getJSONObject(ListingTag.MainImage);
                ListingImage listingImage = new ListingImage();
                if (imageListing.getString(ListingTag.Image.url_170x135) != null) {
                    listingImage.setUrl170x135(imageListing
                            .getString(ListingTag.Image.url_170x135));
                }
                listing.setListingImage(listingImage);
                resultsList.add(listing);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultsList;
    }
}
