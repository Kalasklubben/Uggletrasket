package com.example.johan.uggletrasket.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.os.Handler;

import com.example.johan.uggletrasket.R;

/**
 * Created by Bangen on 15-05-24.
 */
public class StartActivity extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //New handler to begin our MainActivity
         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Create an Intent that starts Mainactivity
                Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
                StartActivity.this.startActivity(mainIntent);
                StartActivity.this.finish();
                overridePendingTransition(R.animator.fadein,R.animator.fadeout);
            }
        }, 2000);
    }
}