package com.example.johan.uggletrasket;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends ActionBarActivity {

    //Declaring

    private ImageButton next, addQuizButton, statsButton, editQuizButton;
    private QuizListFragment dialogOne;
    private AddToQuizFragment dialogTwo;
    FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect ID to button
        next = (ImageButton) findViewById(R.id.answerButton);
        addQuizButton= (ImageButton) findViewById(R.id.addQuestButton);
        statsButton = (ImageButton) findViewById(R.id.statistics);
        editQuizButton = (ImageButton) findViewById(R.id.editQuizButton);

        manager = getFragmentManager();

        //Listener for next button
        View.OnClickListener listNextListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                dialogOne = new QuizListFragment("");
                dialogOne.show(manager, "dialog");
            }
        };
        next.setOnClickListener(listNextListener);

        View.OnClickListener listAddListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddQuiz.class));
                overridePendingTransition(R.animator.push_left_in,R.animator.push_left_out);
            }
        };
        addQuizButton.setOnClickListener(listAddListener);

        View.OnClickListener statisticListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(MainActivity.this, Statistics.class));

                dialogOne = new QuizListFragment("STAT");
                dialogOne.show(manager, "dialog");
            }
        };

        statsButton.setOnClickListener(statisticListener);

        View.OnClickListener editButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialogTwo = new AddToQuizFragment();
                dialogOne = new QuizListFragment("EDIT");
                dialogOne.show(manager, "dialog");
            }
        };
        editQuizButton.setOnClickListener(editButtonListener);
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

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Exit app?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

