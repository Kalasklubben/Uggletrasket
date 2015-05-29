package com.example.johan.uggletrasket.layout;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.johan.uggletrasket.R;

public class MainActivity extends ActionBarActivity {

    private ImageButton playButton, addQuizButton, statsButton, editQuizButton;
    private QuizListFragment quizList;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        playButton = (ImageButton) findViewById(R.id.answerButton);
        addQuizButton= (ImageButton) findViewById(R.id.addQuestButton);
        statsButton = (ImageButton) findViewById(R.id.statistics);
        editQuizButton = (ImageButton) findViewById(R.id.editQuizButton);
        manager = getFragmentManager();

        //Listener for playButton button. Show list of quizzes.
        View.OnClickListener listNextListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quizList = new QuizListFragment("");
                quizList.show(manager, "dialog");
            }
        };
        playButton.setOnClickListener(listNextListener);

        //Listener for add quiz button. Navigate to AddQuiz activity.
        View.OnClickListener listAddListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddQuizActivity.class));
                overridePendingTransition(R.animator.push_left_in,R.animator.push_left_out);
            }
        };
        addQuizButton.setOnClickListener(listAddListener);

        //Listener for statistics button. Show list of quizzes.
        View.OnClickListener statisticListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizList = new QuizListFragment("STAT");
                quizList.show(manager, "dialog");
            }
        };
        statsButton.setOnClickListener(statisticListener);

        //Listener for editQuiz button. Show list of quizzes.
        View.OnClickListener editButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizList = new QuizListFragment("EDIT");
                quizList.show(manager, "dialog");
            }
        };
        editQuizButton.setOnClickListener(editButtonListener);
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
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

