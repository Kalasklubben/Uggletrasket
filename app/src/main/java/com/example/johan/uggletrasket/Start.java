package com.example.johan.uggletrasket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import java.util.concurrent.TimeUnit;
import android.os.Handler;

/**
 * Created by Bangen on 15-05-24.
 */
public class Start extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Start.this, MainActivity.class);
                Start.this.startActivity(mainIntent);
                Start.this.finish();
                overridePendingTransition(R.animator.fadein,R.animator.fadeout);
            }
        }, 2000);
    }
}