package com.example.johan.uggletrasket.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.os.Handler;

import com.example.johan.uggletrasket.R;

/**
 * Manages the layout that handles the application splash screen
 */
public class StartActivity extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //handles the splash screen time delay and forwarding to the start page
         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
                StartActivity.this.startActivity(mainIntent);
                StartActivity.this.finish();
                overridePendingTransition(R.animator.fadein,R.animator.fadeout);
            }
        }, 1500);
    }
}