package com.kanonjitech.phoneUseTracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Main2Activity extends AppCompatActivity {

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    public static String newDateAwake;

    public static Long secAwake;
    public static Long minsAwake;
    public static Long hrsAwake;
    public static Long dysAwake;

    private int Save1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Save1 = AlarmReceiver.getCountAwake1(Main2Activity.this);
        if(Save1 == 1)
        {
            MainActivity.setDate(Main2Activity.this);
            AlarmReceiver.Save1 = 0;
            AlarmReceiver.SaveCounterAwake1(Main2Activity.this);
            Log.d("yahoo000000Awake", "" + AlarmReceiver.Save1);
        }

        MobileAds.initialize(this, "ca-app-pub-5053096756111759~5139478320");
        //real: ca-app-pub-5053096756111759~5139478320
        //Test: ca-app-pub-3940256099942544~3347511713

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

        MainActivity.deviceDead = false;

        Log.d("maaan", DataPerAwake.getRecDiff(Main2Activity.this));

        TextView c = (TextView) findViewById(R.id.recentDiff);
        c.setText(DataPerAwake.getRecDiff(Main2Activity.this));

        TextView c1 = (TextView) findViewById(R.id.count1);
        c1.setText(getBestDiffAwake1(Main2Activity.this));

        Button btnNext = (Button)findViewById(R.id.sleep);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Main2Activity.this, MainActivity.class));

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

                finish();

            }
        });
    }

    public void resetBest(View v){

        DataPerAwake2.DayBestAwake1 = 0;
        DataPerAwake2.saveLongDayBest1(Main2Activity.this);
        DataPerAwake2.HourBestAwake1 = 0;
        DataPerAwake2.saveLongHourBest1(Main2Activity.this);
        DataPerAwake2.MinBestAwake1 = 0;
        DataPerAwake2.saveLongMinBest1(Main2Activity.this);
        DataPerAwake2.SecBestAwake1 = 0;
        DataPerAwake2.saveLongSecBest1(Main2Activity.this);

        setBestDiffAwake1(Main2Activity.this, Long.toString(DataPerAwake2.getLongDayBest1(Main2Activity.this)) + ":" + Long.toString(DataPerAwake2.getLongHourBest1(Main2Activity.this)) + ":" + Long.toString(DataPerAwake2.getLongMinBest1(Main2Activity.this)) + ":" + Long.toString(DataPerAwake2.getLongSecBest1(Main2Activity.this)));

        Log.d("reset", getBestDiffAwake1(Main2Activity.this));

        TextView c1 = (TextView) findViewById(R.id.count1);
        c1.setText("" + getBestDiffAwake1(Main2Activity.this));

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
        intent.putExtra(Intent.EXTRA_TEXT, "I used my phone for " + getBestDiffAwake1(Main2Activity.this) + " (Days:Hours:Minutes:Seconds). HAHA try and beat me. http://play.google.com/store/apps/details?id=" + Main2Activity.this.getPackageName());
        Main2Activity.this.startActivity(Intent.createChooser(intent, "Share"));

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }

    }

    public static void setDateAwake(Context context) {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:mm:ss a");
        String dateToStr = format.format(today);
        System.out.println(dateToStr);
        Log.d("poop", dateToStr);

        newDateAwake = dateToStr;

        DataPerAwake.setDate(context, newDateAwake.toString());
    }

    public static void PrintAwake(Context context) {
        String firstTime = DataPerAwake.getDate(context);
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


            secAwake = seconds-(minutes*60);
            minsAwake = minutes-(hours*60);
            hrsAwake = hours-(days*24);
            dysAwake = days;


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

        SecAwake1 = secAwake;
        saveLongSecAwake1(context);

        MinAwake1 = minsAwake;
        saveLongMinAwake1(context);

        HourAwake1 = hrsAwake;
        saveLongHourAwake1(context);

        DayAwake1 = dysAwake;
        saveLongDayAwake1(context);

        DataPerAwake.setRecDiff(context, Long.toString(getLongDayAwake1(context)) + ":" + Long.toString(getLongHourAwake1(context)) + ":" + Long.toString(getLongMinAwake1(context)) + ":" + Long.toString(getLongSecAwake1(context)));

        if (dysAwake > DataPerAwake2.getLongDayBest1(context)) {
            setBestDiffAwake1(context, Long.toString(getLongDayAwake1(context)) + ":" + Long.toString(getLongHourAwake1(context)) + ":" + Long.toString(getLongMinAwake1(context)) + ":" + Long.toString(getLongSecAwake1(context)));
            DataPerAwake2.DayBestAwake1 = dysAwake;
            DataPerAwake2.saveLongDayBest1(context);
            DataPerAwake2.HourBestAwake1 = hrsAwake;
            DataPerAwake2.saveLongHourBest1(context);
            DataPerAwake2.MinBestAwake1 = minsAwake;
            DataPerAwake2.saveLongMinBest1(context);
            DataPerAwake2.SecBestAwake1 = secAwake;
            DataPerAwake2.saveLongSecBest1(context);
        } else if (hrsAwake > DataPerAwake2.getLongHourBest1(context) && dysAwake == DataPerAwake2.getLongDayBest1(context)) {
            setBestDiffAwake1(context, Long.toString(getLongDayAwake1(context)) + ":" + Long.toString(getLongHourAwake1(context)) + ":" + Long.toString(getLongMinAwake1(context)) + ":" + Long.toString(getLongSecAwake1(context)));
            DataPerAwake2.DayBestAwake1 = dysAwake;
            DataPerAwake2.saveLongDayBest1(context);
            DataPerAwake2.HourBestAwake1 = hrsAwake;
            DataPerAwake2.saveLongHourBest1(context);
            DataPerAwake2.MinBestAwake1 = minsAwake;
            DataPerAwake2.saveLongMinBest1(context);
            DataPerAwake2.SecBestAwake1 = secAwake;
            DataPerAwake2.saveLongSecBest1(context);
        } else if (minsAwake > DataPerAwake2.getLongMinBest1(context) && hrsAwake == DataPerAwake2.getLongHourBest1(context) && dysAwake == DataPerAwake2.getLongDayBest1(context)) {
            setBestDiffAwake1(context, Long.toString(getLongDayAwake1(context)) + ":" + Long.toString(getLongHourAwake1(context)) + ":" + Long.toString(getLongMinAwake1(context)) + ":" + Long.toString(getLongSecAwake1(context)));
            DataPerAwake2.DayBestAwake1 = dysAwake;
            DataPerAwake2.saveLongDayBest1(context);
            DataPerAwake2.HourBestAwake1 = hrsAwake;
            DataPerAwake2.saveLongHourBest1(context);
            DataPerAwake2.MinBestAwake1 = minsAwake;
            DataPerAwake2.saveLongMinBest1(context);
            DataPerAwake2.SecBestAwake1 = secAwake;
            DataPerAwake2.saveLongSecBest1(context);
        } else if (secAwake > DataPerAwake2.getLongSecBest1(context) && minsAwake == DataPerAwake2.getLongMinBest1(context) && hrsAwake == DataPerAwake2.getLongHourBest1(context)&& dysAwake == DataPerAwake2.getLongDayBest1(context)) {
            setBestDiffAwake1(context, Long.toString(getLongDayAwake1(context)) + ":" + Long.toString(getLongHourAwake1(context)) + ":" + Long.toString(getLongMinAwake1(context)) + ":" + Long.toString(getLongSecAwake1(context)));
            DataPerAwake2.DayBestAwake1 = dysAwake;
            DataPerAwake2.saveLongDayBest1(context);
            DataPerAwake2.HourBestAwake1 = hrsAwake;
            DataPerAwake2.saveLongHourBest1(context);
            DataPerAwake2.MinBestAwake1 = minsAwake;
            DataPerAwake2.saveLongMinBest1(context);
            DataPerAwake2.SecBestAwake1 = secAwake;
            DataPerAwake2.saveLongSecBest1(context);
        } else {

        }

    }

    private static SharedPreferences prefs;

    // declares variable as int with a value
    public static long SecAwake1 = 0;
    // decales variable as string
    public static String secAwake1 = "SecAwake1";

    // declares variable as int with a value
    public static long MinAwake1 = 0;
    // decales variable as string
    public static String minAwake1 = "MinAwake1";

    // declares variable as int with a value
    public static long HourAwake1 = 0;
    // decales variable as string
    public static String hourAwake1 = "HourAwake1";

    // declares variable as int with a value
    public static long DayAwake1 = 0;
    // decales variable as string
    public static String dayAwake1 = "DayAwake1";

    // Saves counter when called
    public static void saveLongSecAwake1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(secAwake1, SecAwake1).commit();

    }

    //method used to get the value
    public static Long getLongSecAwake1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(secAwake1, SecAwake1);

        return l1;
    }

    // Saves counter when called
    public static void saveLongHourAwake1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(hourAwake1, HourAwake1).commit();

    }

    //method used to get the value
    public static Long getLongHourAwake1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(hourAwake1, HourAwake1);

        return l1;
    }

    // Saves counter when called
    public static void saveLongDayAwake1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(dayAwake1, DayAwake1).commit();

    }

    //method used to get the value
    public static Long getLongDayAwake1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(dayAwake1, DayAwake1);

        return l1;
    }

    // Saves counter when called
    public static void saveLongMinAwake1(Context context) {
        prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        prefs.edit().putLong(minAwake1, MinAwake1).commit();

    }

    //method used to get the value
    public static Long getLongMinAwake1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", context.MODE_PRIVATE);
        long l1= prefs.getLong(minAwake1, MinAwake1);

        return l1;
    }

    // saves input string when called
    public static void setBestDiffAwake1(Context context, String BestDiffAwake1) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("bestDiffAwake1", BestDiffAwake1);
        editor.commit();
    }

    // gets input string when called
    public static String getBestDiffAwake1(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("com.kanonjitech.phoneUseTracker", 0);
        return prefs.getString("bestDiffAwake1", "0:0:0:0");
    }


}
