package com.example.johan.uggletrasket;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.app.Activity;


public class MainActivity extends ActionBarActivity {

    //Declaring
    private Button next, addQuest, updateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect ID to button
        next = (Button) findViewById(R.id.nextButton);
        addQuest= (Button) findViewById(R.id.addQuestButton);
        updateButton = (Button) findViewById(R.id.statistics);

        //Listener for next button
        View.OnClickListener listNext = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QuestionActivity.class));
            }
        };

        next.setOnClickListener(listNext);

        View.OnClickListener listAdd = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddQuestion.class));
            }
        };

        addQuest.setOnClickListener(listAdd);

        View.OnClickListener statistic = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, statistics.class));
            }
        };

        updateButton.setOnClickListener(statistic);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
