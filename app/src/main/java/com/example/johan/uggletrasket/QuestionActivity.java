package com.example.johan.uggletrasket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

/**
 * Created by David on 27/04/2015.
 */
public class QuestionActivity extends ActionBarActivity{

    //Declaring
    private Button answerA, answerB, answerC, answerD;
    private TextView questionView;
    private Question question;
    private int correctButtonID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //Connect ID to buttons
        answerA = (Button) findViewById(R.id.alternativeA);
        answerB = (Button) findViewById(R.id.alternativeB);
        answerC = (Button) findViewById(R.id.alternativeC);
        answerD = (Button) findViewById(R.id.alternativeD);
        questionView = (TextView) findViewById(R.id.question);

        //Listener for buttons
        View.OnClickListener list = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == correctButtonID){
                    //Correct answer
                    showMessage("Correct!");
                }else{
                    //Wrong answer
                    showMessage("Wrong!");
                }
            }
        };

        //Listener on buttons
        answerA.setOnClickListener(list);
        answerB.setOnClickListener(list);
        answerC.setOnClickListener(list);
        answerD.setOnClickListener(list);

        //Create test question
        createQuestion();

        //Load question
        loadQuestion();
    }

    //Method creating a question, for TEST PURPOSE ONLY
    private void createQuestion() {
        question = new Question();
        question.setQuestion(getResources().getString(R.string.quest1));
        question.setAnswer(getResources().getString(R.string.correct1));

        try {
            question.addAlternative(getResources().getString(R.string.alt1A));
            question.addAlternative(getResources().getString(R.string.alt1B));
            question.addAlternative(getResources().getString(R.string.alt1C));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Method to load question to private instance
    private void loadQuestion() {
        //this.question = ....
        //
        //Also set correctButton to the same ID as the button holding the right answer

        //Static display, MUST be changed
        questionView.setText(question.getQuestion());
        answerA.setText(question.getAnswer());
        correctButtonID = answerA.getId();
        String[] alts = question.getAlternatives();
        answerB.setText(alts[0]);
        answerC.setText(alts[1]);
        answerD.setText(alts[2]);

    }

    //Method for showing pop up, for the time being
    private void showMessage(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(QuestionActivity.this).create();
        alertDialog.setTitle("Uggletrasket!");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
