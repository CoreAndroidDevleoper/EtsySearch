
package com.etsy.search.utils;

import java.io.File;

import android.content.Context;

/***
 * Temp caching bitmaps in file cache
 */
public class FileCache {

    private static final String TAG = "FileCache";
    private File mCacheDirectory;

    public FileCache(Context context) {
        // Find the dir to save cached images
        mCacheDirectory = context.getExternalCacheDir();
        if (!mCacheDirectory.exists()) {
            mCacheDirectory.mkdirs();
        }
    }

    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        File f = new File(mCacheDirectory, filename);
        return f;

    }

    public void clear() {
        File[] files = mCacheDirectory.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }

}
