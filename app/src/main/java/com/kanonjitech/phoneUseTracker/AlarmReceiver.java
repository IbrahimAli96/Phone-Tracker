package com.kanonjitech.phoneUseTracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by Ibrahim on 12/08/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    private final static String TAG = "BroadcastService";

    private static SharedPreferences prefs;

    // declares variable as int with a value
    public static int Save1 = 0;
    // decales variable as string
    public static String save1 = "SaveCounter1";

    // declares variable as int with a value
    public static int SaveAwake1 = 1;
    // decales variable as string
    public static String saveAwake1 = "SaveCounterAwake1";

    // Saves counter when called
    public static void SaveCounter1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putInt(save1, Save1).commit();

    }

    //method used to get the value
    public static int getCount1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        int c1= prefs.getInt(save1, Save1);

        return c1;
    }

    // Saves counter when called
    public static void SaveCounterAwake1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putInt(saveAwake1, SaveAwake1).commit();

    }

    //method used to get the value
    public static int getCountAwake1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        int c1= prefs.getInt(saveAwake1, SaveAwake1);

        return c1;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        //get and send location information
        System.out.println("fired");

        context.startService(new Intent(context, MainActivity.class));

        wakeScreen(context);

        Log.d("mochi", "" + MainActivity.getAdShow(context));

//        if(MainActivity.getAdShow(context) < 1500) {
//            MainActivity.AdShow = MainActivity.getAdShow(context);
//            MainActivity.AdShow++;
//            MainActivity.saveAdShow(context);
//        }

    }

    private void wakeScreen(Context context){

        // Waking the screen so the user will see the notification
        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);


        boolean isScreenOn;

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH)
            isScreenOn = pm.isScreenOn();
        else
            isScreenOn = pm.isInteractive();

//        Save1 = getCount1(context);
//
//        SaveAwake1 = getCountAwake1(context);
//
//        if(isScreenOn && MainActivity.getAdShow(context) == 1500 && MainActivity.deviceDead == true)
//        {
//
//            Intent intent = new Intent(context, ShowAd.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            context.startActivity(intent);
//
//            MainActivity.AdShow = 0;
//            MainActivity.saveAdShow(context);
//        }

        if (!isScreenOn && getCount1(context) == 0) {
            MainActivity.setDate(context);
            Save1 = 1;
            SaveCounter1(context);
            Log.d("yahooAwake", "" + Save1);
        }
        if (!isScreenOn && getCountAwake1(context) == 0) {
            Main2Activity.PrintAwake(context);
            SaveAwake1 = 1;
            SaveCounterAwake1(context);
            Log.d("yahooAwake", "" + SaveAwake1);
        }

        if(isScreenOn && getCount1(context) == 1 )
        {
            MainActivity.Print(context);
            Save1 = 0;
            SaveCounter1(context);
            Log.d("yahoo000000", "" + Save1);
        }
        if(isScreenOn && getCountAwake1(context) == 1 )
        {
            Main2Activity.setDateAwake(context);
            SaveAwake1 = 0;
            SaveCounterAwake1(context);
            Log.d("yahoo000000Awake", "" + SaveAwake1);
        }

    }
}
