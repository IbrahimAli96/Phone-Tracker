package com.kanonjitech.phoneUseTracker;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ibrahim on 12/08/2018.
 */

public class DataPerAwake {

    // saves input string when called
    public static void setDate(Context context, String DateAwake1) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("dateAwake1", DateAwake1);
        editor.commit();
    }

    // gets input string when called
    public static String getDate(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", 0);
        return prefs.getString("dateAwake1", "0:0:0:0");
    }

    // saves input string when called
    public static void setRecDiff(Context context, String RecDiffAwake) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("recDiffAwake", RecDiffAwake);
        editor.commit();
    }

    // gets input string when called
    public static String getRecDiff(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", 0);
        return prefs.getString("recDiffAwake", "0:0:0:0");
    }

}
