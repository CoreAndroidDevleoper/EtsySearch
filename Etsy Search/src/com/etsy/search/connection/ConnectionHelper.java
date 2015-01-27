
package com.etsy.search.connection;

import java.util.ArrayList;

import com.etsy.search.connection.parser.ListingParser;
import com.etsy.search.data.Listing;
import com.etsy.search.data.RequestHelper;

/**
 * Connection Helper Class for connectivity related stuff
 */
public class ConnectionHelper {

    private static final String TAG = ConnectionHelper.class.getSimpleName();
    private static ConnectionHelper sInstance = new ConnectionHelper();;

    private ConnectionHelper() {
    }

    public static ConnectionHelper getInstance() {
        if (sInstance == null) {
            sInstance = new ConnectionHelper();
        }
        return sInstance;
    }

    /****
     * Simplified API to get the request in single API format
     * 
     * @param searchTerm
     * @param includeImages
     * @return
     */
    public ArrayList<Listing> getListings(String searchTerm, boolean includeImages, int sortOrder,
            int offset, int limit) {
        HttpConnection httpConnection = new HttpConnection();
        String url = RequestHelper.API + RequestHelper.KEY;
        if (includeImages) {
            url = url + RequestHelper.INCLUDE_IMAGE;
        }
        if (searchTerm != null) {
            url = url + RequestHelper.SEARCH + searchTerm;
        }

        url = url + RequestHelper.getSortQuery(sortOrder);
        url = url + RequestHelper.OFFSET + offset;
        url = url + RequestHelper.LIMIT + limit;

        url = url.replaceAll(" ", "%20");
        String response = httpConnection.makeHttpConnection(url, HttpConnection.GET);
        ListingParser listingParser = new ListingParser();
        return listingParser.getListing(response);
    }
}
