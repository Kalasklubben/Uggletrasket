package com.example.johan.uggletrasket.layout;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.johan.uggletrasket.R;

public class MainActivity extends ActionBarActivity {

    //Declaration of all inputs and buttons.
    private ImageButton play, addQuizButton, statsButton, editQuizButton;
    private QuizListFragment quizList;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Connect activity with layout
        setContentView(R.layout.activity_main);

        //Connect all inputs and buttons with respective layout id
        play = (ImageButton) findViewById(R.id.answerButton);
        addQuizButton= (ImageButton) findViewById(R.id.addQuestButton);
        statsButton = (ImageButton) findViewById(R.id.statistics);
        editQuizButton = (ImageButton) findViewById(R.id.editQuizButton);

        //Assign fragment manager
        manager = getFragmentManager();

        //Listener for play button. Show list of quizzes.
        View.OnClickListener listNextListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quizList = new QuizListFragment("");
                quizList.show(manager, "dialog");
            }
        };

        //Add listener to play button
        play.setOnClickListener(listNextListener);

        //Listener for add quiz button. Navigate to AddQuiz activity.
        View.OnClickListener listAddListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddQuizActivity.class));
                overridePendingTransition(R.animator.push_left_in,R.animator.push_left_out);
            }
        };

        //Add listener to addQuiz button
        addQuizButton.setOnClickListener(listAddListener);

        //Listener for statistics button. Show list of quizzes.
        View.OnClickListener statisticListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(MainActivity.this, Statistics.class));

                quizList = new QuizListFragment("STAT");
                quizList.show(manager, "dialog");
            }
        };

        //Add listener to statistics button
        statsButton.setOnClickListener(statisticListener);

        //Listener for editQuiz button. Show list of quizzes.
        View.OnClickListener editButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialogTwo = new AddToQuizFragment();
                quizList = new QuizListFragment("EDIT");
                quizList.show(manager, "dialog");
            }
        };

        //Add listener to editQuiz button.
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

    //Exit the app when pressing the back button.
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

