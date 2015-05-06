package com.example.johan.uggletrasket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Bangen on 15-05-06.
 */
public class ResultActivity extends Activity {

    private TextView resultView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_result);
            resultView = (TextView) findViewById(R.id.textView3);

            Bundle bundle = getIntent().getExtras();
            String result = bundle.getString("result");
            resultView.setText(result);
        }
}
