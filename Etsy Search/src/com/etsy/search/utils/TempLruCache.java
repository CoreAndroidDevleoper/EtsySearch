
package com.etsy.search.utils;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;

import com.etsy.search.EtsyLogs;

/***
 * Implement the temporary LRU cache for getting images from the memory.
 */
public class TempLruCache {

    private static final String TAG = TempLruCache.class.getSimpleName();

    // Last argument true for LRU ordering
    private Map<String, Bitmap> mCache = Collections
            .synchronizedMap(new LinkedHashMap<String, Bitmap>(10, 1.5f, true));

    // Current allocated size
    private long mAllotedSize = 0;

    // Max memory in bytes
    private long mMaxMemoryLimit = 1000000;

    public TempLruCache() {
        // Use 25% of available heap size
        setLimit(Runtime.getRuntime().maxMemory() / 4);
    }

    public void setLimit(long new_limit) {
        mMaxMemoryLimit = new_limit;
        EtsyLogs.d(TAG, "TempLruCache will use up to " + mMaxMemoryLimit / 1024. / 1024. + "MB");
    }

    public Bitmap get(String id) {
        try {
            if (!mCache.containsKey(id))
                return null;
            return mCache.get(id);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void put(String id, Bitmap bitmap) {
        try {
            if (mCache.containsKey(id))
                mAllotedSize -= getSizeInBytes(mCache.get(id));
            mCache.put(id, bitmap);
            mAllotedSize += getSizeInBytes(bitmap);
            checkSize();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void checkSize() {
        EtsyLogs.d(TAG, "cache size=" + mAllotedSize + " length=" + mCache.size());
        if (mAllotedSize > mMaxMemoryLimit) {
            // Least recently accessed item will be the first one iterated
            Iterator<Entry<String, Bitmap>> iter = mCache.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<String, Bitmap> entry = iter.next();
                mAllotedSize -= getSizeInBytes(entry.getValue());
                iter.remove();
                if (mAllotedSize <= mMaxMemoryLimit)
                    break;
            }
            EtsyLogs.d(TAG, "Clean cache. New size " + mCache.size());
        }
    }

    public void clear() {
        try {
            mCache.clear();
            mAllotedSize = 0;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    long getSizeInBytes(Bitmap bitmap) {
        if (bitmap == null)
            return 0;
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
