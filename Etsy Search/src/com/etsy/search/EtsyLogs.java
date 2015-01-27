
package com.etsy.search;

import android.util.Log;

/**
 * Single File for capture all the logs for this app. *
 */
public class EtsyLogs {
    private static String MAIN_TAG = "ETSY - ";
    private static boolean ENABLE_LOGS = false;

    public static void d(String tag, String msg) {
        if (ENABLE_LOGS)
            Log.d(MAIN_TAG + tag, msg);
    }

    public static void v(String tag, String msg) {
        if (ENABLE_LOGS)
            Log.v(MAIN_TAG + tag, msg);
    }

    public static void i(String tag, String msg) {
        if (ENABLE_LOGS)
            Log.i(MAIN_TAG + tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(MAIN_TAG + tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(MAIN_TAG + tag, msg);
    }

}
