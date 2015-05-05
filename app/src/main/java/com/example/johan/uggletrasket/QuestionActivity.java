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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by David on 27/04/2015.
 */
public class QuestionActivity extends ActionBarActivity{

    //Declaring
    private Button[] buttonArray = new Button[4];
    private TextView questionView;
    private QuestionList questions;
    private int correctButtonID;
    private int correctAnswers = 0, wrongAnswers = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //Alex code
        StrictMode.enableDefaults(); //STRICT MODE ENABLED

        //Get question from LoadQuestion class, using string from strings.xml
        this.questions = LoadQuestions.getData(getResources().getString(R.string.getAllQuestions));

        //Connect ID to buttons
        buttonArray[0] = (Button) findViewById(R.id.alternativeA);
        buttonArray[1] = (Button) findViewById(R.id.alternativeB);
        buttonArray[2] = (Button) findViewById(R.id.alternativeC);
        buttonArray[3] = (Button) findViewById(R.id.alternativeD);
        questionView = (TextView) findViewById(R.id.question);

        //Listener for buttons
        View.OnClickListener list = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == correctButtonID) {
                    correctAnswers++;
                    Toast.makeText(QuestionActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    //Wrong answer
                    wrongAnswers++;
                    Toast.makeText(QuestionActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
                }
                if (questions.endOfList()) {
                    Intent i = new Intent(QuestionActivity.this, QuizResult.class);
                    i.putExtra("correct", correctAnswers);
                    i.putExtra("wrong", wrongAnswers);
                    i.putExtra("questions", questions.getQuestions());

                    startActivity(i);
                } else {
                    displayQuestion(questions.getNext());
                }
            }
        };

        //Listener on buttons
        for (int i = 0; i < 4; i++)
            buttonArray[i].setOnClickListener(list);
        displayQuestion(questions.getNext());
    }

    //Method to display question to private instance
    private void displayQuestion(Question question) {

        //Displaying alternatives random
        questionView.setText(question.getQuestion());
        ArrayList<String> displayArray = new ArrayList<String>();

        displayArray.add(question.getAnswer());
        String[] alts = question.getAlternatives();

        for(int i = 0; i < 3; i++)
            displayArray.add(alts[i]);

        Collections.shuffle(displayArray);

        for(int i = 0; i < 4 ; i++) {
            buttonArray[i].setText(displayArray.get(i));
            if(buttonArray[i].getText() == question.getAnswer())
                correctButtonID = buttonArray[i].getId();
        }
    }

    //Method for showing pop up, for the time being
    private void showMessage(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(QuestionActivity.this).create();
        alertDialog.setTitle(getResources().getString(R.string.app_name));
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


