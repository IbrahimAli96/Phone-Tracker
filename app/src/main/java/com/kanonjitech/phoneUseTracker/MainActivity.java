package com.kanonjitech.phoneUseTracker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    public static InterstitialAd mInterstitialAd;

    private int Save1;

    public static boolean deviceDead = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-5053096756111759~5139478320");
        //real: ca-app-pub-5053096756111759~5139478320
        //Test:ca-app-pub-3940256099942544~3347511713
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5053096756111759/1245315406");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        //real: ca-app-pub-5053096756111759/1245315406
        //Test: ca-app-pub-3940256099942544/1033173712

        //Banner
        //real: ca-app-pub-5053096756111759/8033660009
        //Test: ca-app-pub-3940256099942544/6300978111

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        deviceDead = false;

        Log.d("maaan", DataPer.getRecDiff(MainActivity.this));

        Save1 = AlarmReceiver.getCount1(MainActivity.this);
        if(Save1 == 1)
        {
            MainActivity.Print(MainActivity.this);
            AlarmReceiver.Save1 = 0;
            AlarmReceiver.SaveCounter1(MainActivity.this);
            Log.d("yahoo000000", "" + AlarmReceiver.Save1);
        }

        TextView c = (TextView) findViewById(R.id.recentDiff);
        c.setText(DataPer.getRecDiff(MainActivity.this));

        TextView c1 = (TextView) findViewById(R.id.count1);
        c1.setText(getBestDiff1(MainActivity.this));

        AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                60*1000,
                pendingIntent);


//        btn = (Button)findViewById(R.id.awake);
//        btn.performClick();

        Button btnNext = (Button)findViewById(R.id.awake);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Main2Activity.class));

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

                finish();

            }
        });

        Log.d("mochi", "" + getRate(MainActivity.this));

        if(getRate(MainActivity.this) < 10) {
            Rate++;
            saveRate(MainActivity.this);
        }

        if(getRate(MainActivity.this) == 10) {
            startActivity(new Intent(MainActivity.this, RateApp.class));
        }

    }

    public void resetBest(View v){

        DataPer2.DayBest1 = 0;
        DataPer2.saveLongDayBest1(MainActivity.this);
        DataPer2.HourBest1 = 0;
        DataPer2.saveLongHourBest1(MainActivity.this);
        DataPer2.MinBest1 = 0;
        DataPer2.saveLongMinBest1(MainActivity.this);
        DataPer2.SecBest1 = 0;
        DataPer2.saveLongSecBest1(MainActivity.this);

        setBestDiff1(MainActivity.this, Long.toString(DataPer2.getLongDayBest1(MainActivity.this)) + ":" + Long.toString(DataPer2.getLongHourBest1(MainActivity.this)) + ":" + Long.toString(DataPer2.getLongMinBest1(MainActivity.this)) + ":" + Long.toString(DataPer2.getLongSecBest1(MainActivity.this)));

        Log.d("reset", getBestDiff1(MainActivity.this));

        TextView c1 = (TextView) findViewById(R.id.count1);
        c1.setText("" + getBestDiff1(MainActivity.this));

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }

    }

    public void shareBest(View v){

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "I didn't use my phone for " + getBestDiff1(MainActivity.this) + " (Days:Hours:Minutes:Seconds). HAHA try and beat me. http://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName());
        MainActivity.this.startActivity(Intent.createChooser(intent, "Share"));

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }

    }

    public static void setDate(Context context) {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:mm:ss a");
        String dateToStr = format.format(today);
        System.out.println(dateToStr);
        Log.d("poop", dateToStr);

        newDate = dateToStr;

        DataPer.setDate(context, newDate.toString());
    }

    public static String newDate;

    public static Long sec;
    public static Long mins;
    public static Long hrs;
    public static Long dys;


    public static void Print(Context context) {
        String firstTime = DataPer.getDate(context);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        try {

            Date oldDate = dateFormat.parse(firstTime);
            System.out.println(oldDate);

            Date currentDate = new Date();

            long diff = currentDate.getTime() - oldDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;


            sec = seconds-(minutes*60);
            mins = minutes-(hours*60);
            hrs = hours-(days*24);
            dys = days;

            if (oldDate.before(currentDate)) {

                Log.e("oldDate", "is previous date");
                Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
                        + " hours: " + hours + " days: " + days);

            }

            // Log.e("toyBornTime", "" + toyBornTime);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        //String secLong = Long.toString(sec);

        Sec1 = sec;
        saveLongSec1(context);

        Min1 = mins;
        saveLongMin1(context);

        Hour1 = hrs;
        saveLongHour1(context);

        Day1 = dys;
        saveLongDay1(context);

        DataPer.setRecDiff(context, Long.toString(getLongDay1(context)) + ":" + Long.toString(getLongHour1(context)) + ":" + Long.toString(getLongMin1(context)) + ":" + Long.toString(getLongSec1(context)));

        if (dys > DataPer2.getLongDayBest1(context)) {
            setBestDiff1(context, Long.toString(getLongDay1(context)) + ":" + Long.toString(getLongHour1(context)) + ":" + Long.toString(getLongMin1(context)) + ":" + Long.toString(getLongSec1(context)));
            DataPer2.DayBest1 = dys;
            DataPer2.saveLongDayBest1(context);
            DataPer2.HourBest1 = hrs;
            DataPer2.saveLongHourBest1(context);
            DataPer2.MinBest1 = mins;
            DataPer2.saveLongMinBest1(context);
            DataPer2.SecBest1 = sec;
            DataPer2.saveLongSecBest1(context);
        } else if (hrs > DataPer2.getLongHourBest1(context) && dys == DataPer2.getLongDayBest1(context)) {
            setBestDiff1(context, Long.toString(getLongDay1(context)) + ":" + Long.toString(getLongHour1(context)) + ":" + Long.toString(getLongMin1(context)) + ":" + Long.toString(getLongSec1(context)));
            DataPer2.DayBest1 = dys;
            DataPer2.saveLongDayBest1(context);
            DataPer2.HourBest1 = hrs;
            DataPer2.saveLongHourBest1(context);
            DataPer2.MinBest1 = mins;
            DataPer2.saveLongMinBest1(context);
            DataPer2.SecBest1 = sec;
            DataPer2.saveLongSecBest1(context);
        } else if (mins > DataPer2.getLongMinBest1(context) && hrs == DataPer2.getLongHourBest1(context) && dys == DataPer2.getLongDayBest1(context)) {
            setBestDiff1(context, Long.toString(getLongDay1(context)) + ":" + Long.toString(getLongHour1(context)) + ":" + Long.toString(getLongMin1(context)) + ":" + Long.toString(getLongSec1(context)));
            DataPer2.DayBest1 = dys;
            DataPer2.saveLongDayBest1(context);
            DataPer2.HourBest1 = hrs;
            DataPer2.saveLongHourBest1(context);
            DataPer2.MinBest1 = mins;
            DataPer2.saveLongMinBest1(context);
            DataPer2.SecBest1 = sec;
            DataPer2.saveLongSecBest1(context);
        } else if (sec > DataPer2.getLongSecBest1(context) && mins == DataPer2.getLongMinBest1(context) && hrs == DataPer2.getLongHourBest1(context)&& dys == DataPer2.getLongDayBest1(context)) {
            setBestDiff1(context, Long.toString(getLongDay1(context)) + ":" + Long.toString(getLongHour1(context)) + ":" + Long.toString(getLongMin1(context)) + ":" + Long.toString(getLongSec1(context)));
            DataPer2.DayBest1 = dys;
            DataPer2.saveLongDayBest1(context);
            DataPer2.HourBest1 = hrs;
            DataPer2.saveLongHourBest1(context);
            DataPer2.MinBest1 = mins;
            DataPer2.saveLongMinBest1(context);
            DataPer2.SecBest1 = sec;
            DataPer2.saveLongSecBest1(context);
        } else {

        }

    }

    private static SharedPreferences prefs;

    // declares variable as int with a value
    public static int AdShow = 0;
    // decales variable as string
    public static String adShow = "AdShow";

    // declares variable as int with a value
    public static int Rate = 0;
    // decales variable as string
    public static String rate = "Rate";

    // declares variable as int with a value
    public static long Sec1 = 0;
    // decales variable as string
    public static String sec1 = "Sec1";

    // declares variable as int with a value
    public static long Min1 = 0;
    // decales variable as string
    public static String min1 = "Min1";

    // declares variable as int with a value
    public static long Hour1 = 0;
    // decales variable as string
    public static String hour1 = "Hour1";

    // declares variable as int with a value
    public static long Day1 = 0;
    // decales variable as string
    public static String day1 = "Day1";

    // Saves counter when called
    public static void saveAdShow(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putInt(adShow, AdShow).commit();

    }

    //method used to get the value
    public static int getAdShow(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        int l1= prefs.getInt(adShow, AdShow);

        return l1;
    }

    // Saves counter when called
    public static void saveRate(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putInt(rate, Rate).commit();

    }

    //method used to get the value
    public static int getRate(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        int l1= prefs.getInt(rate, Rate);

        return l1;
    }

    // Saves counter when called
    public static void saveLongSec1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(sec1, Sec1).commit();

    }

    //method used to get the value
    public static Long getLongSec1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(sec1, Sec1);

        return l1;
    }

    // Saves counter when called
    public static void saveLongHour1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(hour1, Hour1).commit();

    }

    //method used to get the value
    public static Long getLongHour1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(hour1, Hour1);

        return l1;
    }

    // Saves counter when called
    public static void saveLongDay1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(day1, Day1).commit();

    }

    //method used to get the value
    public static Long getLongDay1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(day1, Day1);

        return l1;
    }

    // Saves counter when called
    public static void saveLongMin1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(min1, Min1).commit();

    }

    //method used to get the value
    public static Long getLongMin1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(min1, Min1);

        return l1;
    }

    // saves input string when called
    public static void setBestDiff1(Context context, String BestDiff1) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("bestDiff1", BestDiff1);
        editor.commit();
    }

    // gets input string when called
    public static String getBestDiff1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", 0);
        return prefs.getString("bestDiff1", "0:0:0:0");
    }




}
