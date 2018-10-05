package com.kanonjitech.phoneUseTracker;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class RateApp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_app);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //displays activity in smaller dimensions
        getWindow().setLayout((int)(width*.7), (int)(height*.3));
    }

    public void yes(View v) {

        MainActivity.Rate = 20;
        MainActivity.saveRate(RateApp.this);

        finish();

        rateApp();

        Log.d("mochi", "" + MainActivity.getRate(RateApp.this));

    }

    public void no(View v) {

        MainActivity.Rate = 20;
        MainActivity.saveRate(RateApp.this);

        finish();

        Log.d("mochi", "" + MainActivity.getRate(RateApp.this));
    }

    public void later(View v) {
        MainActivity.Rate = 0;
        MainActivity.saveRate(RateApp.this);

        finish();

        Log.d("mochi", "" + MainActivity.getRate(RateApp.this));

    }

    public void rateApp(){
        Uri uri = Uri.parse("market://details?id=" + RateApp.this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + RateApp.this.getPackageName())));
        }
    }

}
