package com.kanonjitech.phoneUseTracker;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ibrahim on 13/08/2018.
 */

public class DataPer2 {

    private static SharedPreferences prefs;

    // declares variable as int with a value
    public static long SecBest1 = 0;
    // decales variable as string
    public static String secBest1 = "SecBest1";

    // declares variable as int with a value
    public static long MinBest1 = 0;
    // decales variable as string
    public static String minBest1 = "MinBest1";

    // declares variable as int with a value
    public static long HourBest1 = 0;
    // decales variable as string
    public static String hourBest1 = "HourBest1";

    // declares variable as int with a value
    public static long DayBest1 = 0;
    // decales variable as string
    public static String dayBest1 = "DayBest1";

    // Saves counter when called
    public static void saveLongSecBest1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(secBest1, SecBest1).commit();

    }

    //method used to get the value
    public static Long getLongSecBest1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(secBest1, SecBest1);

        return l1;
    }

    // Saves counter when called
    public static void saveLongMinBest1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(minBest1, MinBest1).commit();

    }

    //method used to get the value
    public static Long getLongMinBest1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(minBest1, MinBest1);

        return l1;
    }


    // Saves counter when called
    public static void saveLongHourBest1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(hourBest1, HourBest1).commit();

    }

    //method used to get the value
    public static Long getLongHourBest1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(hourBest1, HourBest1);

        return l1;
    }

    // Saves counter when called
    public static void saveLongDayBest1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(dayBest1, DayBest1).commit();

    }

    //method used to get the value
    public static Long getLongDayBest1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(dayBest1, DayBest1);

        return l1;
    }


}
