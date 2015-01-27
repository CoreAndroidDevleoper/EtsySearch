
package com.etsy.search.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import com.etsy.search.EtsyLogs;
import com.etsy.search.R;

/***
 * Helper Class to load the images and cache them.
 */
public class ImageCacheTask {

    private static final String TAG = ImageCacheTask.class.getSimpleName();
    private final int mPlaceholder = R.drawable.placeholder;

    private TempLruCache mMemoryCache = new TempLruCache();
    private FileCache mFileCache;
    private ExecutorService mExecutorService;
    private Handler mHandler = new Handler();
    private HttpURLConnection mHttpUrlConnection;
    private Map<ImageView, String> mImageViewMap = Collections
            .synchronizedMap(new WeakHashMap<ImageView, String>());
    private static ImageCacheTask sImageCacheTask;

    private ImageCacheTask(Context context) {
        mFileCache = new FileCache(context);
        mExecutorService = Executors.newFixedThreadPool(5);
    }

    public static ImageCacheTask getInstance(Context context) {
        if (sImageCacheTask == null) {
            sImageCacheTask = new ImageCacheTask(context);
        }
        return sImageCacheTask;
    }

    public void displayImage(String url, ImageView imageView) {
        try {

            mImageViewMap.put(imageView, url);
            Bitmap bitmap = mMemoryCache.get(url);

            if (bitmap == null) {
                bitmap = decodeFile(mFileCache.getFile(url));
            }
            if (bitmap != null)
                imageView.setImageBitmap(bitmap);
            else {
                queuePhoto(url, imageView);
                imageView.setImageResource(mPlaceholder);
            }
        } catch (Throwable t) {
            EtsyLogs.e(TAG, t.toString());
            t.printStackTrace();
        }

    }

    private void queuePhoto(String url, ImageView imageView) {
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        mExecutorService.submit(new PhotosLoader(p));
    }

    private Bitmap getBitmap(String url) {
        File f = mFileCache.getFile(url);

        Bitmap b = decodeFile(f);
        if (b != null)
            return b;

        // Download Images from the Internet
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            mHttpUrlConnection = (HttpURLConnection) imageUrl
                    .openConnection();
            mHttpUrlConnection.setConnectTimeout(30000);
            mHttpUrlConnection.setReadTimeout(30000);
            mHttpUrlConnection.setInstanceFollowRedirects(true);
            InputStream is = mHttpUrlConnection.getInputStream();
            OutputStream os = new FileOutputStream(f);
            CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // Decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, o);
            stream1.close();

            // Find the correct scale value. It should be the power of 2.
            // Recommended Size 512
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream stream2 = new FileInputStream(f);
            Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size = 1024;
        try
        {
            byte[] bytes = new byte[buffer_size];
            for (;;)
            {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    // Task for the queue
    private class PhotoToLoad {
        public String url;
        public ImageView imageView;

        public PhotoToLoad(String u, ImageView i) {
            url = u;
            imageView = i;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;

        PhotosLoader(PhotoToLoad photoToLoad) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            try {
                if (imageViewReused(photoToLoad))
                    return;
                Bitmap bmp = getBitmap(photoToLoad.url);
                mMemoryCache.put(photoToLoad.url, bmp);
                if (imageViewReused(photoToLoad))
                    return;
                BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
                mHandler.post(bd);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = mImageViewMap.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    // Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
            bitmap = b;
            photoToLoad = p;
        }

        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            if (bitmap != null)
                photoToLoad.imageView.setImageBitmap(bitmap);
            else
                photoToLoad.imageView.setImageResource(mPlaceholder);
        }
    }

    public void clearCache() {
        mMemoryCache.clear();
        mFileCache.clear();
    }
}
