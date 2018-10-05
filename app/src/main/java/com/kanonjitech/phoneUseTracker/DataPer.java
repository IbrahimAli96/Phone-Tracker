package com.kanonjitech.phoneUseTracker;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ibrahim on 12/08/2018.
 */

public class DataPer {

    // saves input string when called
    public static void setDate(Context context, String Date1) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("date1", Date1);
        editor.commit();
    }

    // gets input string when called
    public static String getDate(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", 0);
        return prefs.getString("date1", "0:0:0:0");
    }

    // saves input string when called
    public static void setRecDiff(Context context, String RecDiff) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("recDiff", RecDiff);
        editor.commit();
    }

    // gets input string when called
    public static String getRecDiff(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", 0);
        return prefs.getString("recDiff", "0:0:0:0");
    }

}
