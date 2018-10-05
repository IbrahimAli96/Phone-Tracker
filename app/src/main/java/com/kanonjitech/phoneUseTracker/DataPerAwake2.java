package com.kanonjitech.phoneUseTracker;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ibrahim on 13/08/2018.
 */

public class DataPerAwake2 {

    private static SharedPreferences prefs;

    // declares variable as int with a value
    public static long SecBestAwake1 = 0;
    // decales variable as string
    public static String secBestAwake1 = "SecBestAwake1";

    // declares variable as int with a value
    public static long MinBestAwake1 = 0;
    // decales variable as string
    public static String minBestAwake1 = "MinBestAwake1";

    // declares variable as int with a value
    public static long HourBestAwake1 = 0;
    // decales variable as string
    public static String hourBestAwake1 = "HourBestAwake1";

    // declares variable as int with a value
    public static long DayBestAwake1 = 0;
    // decales variable as string
    public static String dayBestAwake1 = "DayBestAwake1";

    // Saves counter when called
    public static void saveLongSecBest1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(secBestAwake1, SecBestAwake1).commit();

    }

    //method used to get the value
    public static Long getLongSecBest1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(secBestAwake1, SecBestAwake1);

        return l1;
    }

    // Saves counter when called
    public static void saveLongMinBest1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(minBestAwake1, MinBestAwake1).commit();

    }

    //method used to get the value
    public static Long getLongMinBest1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(minBestAwake1, MinBestAwake1);

        return l1;
    }


    // Saves counter when called
    public static void saveLongHourBest1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(hourBestAwake1, HourBestAwake1).commit();

    }

    //method used to get the value
    public static Long getLongHourBest1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(hourBestAwake1, HourBestAwake1);

        return l1;
    }

    // Saves counter when called
    public static void saveLongDayBest1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(dayBestAwake1, DayBestAwake1).commit();

    }

    //method used to get the value
    public static Long getLongDayBest1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(dayBestAwake1, DayBestAwake1);

        return l1;
    }


}
