package com.example.johan.uggletrasket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DecimalFormat;


public class StatisticsActivity extends Activity {
    /** Called when the activity is first created. */

    private TextView resultView;
    private ImageButton returnButton;
    private String quizID = "";
    private QuestionList ql;
            
;   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //STRICT MODE ENABLED
        /*StrictMode.enableDefaults();*/

        //Connect all inputs and buttons with the layout id
        resultView = (TextView) findViewById(R.id.result);
        returnButton = (ImageButton) findViewById(R.id.return_stats_button);

        //Get quizID
        Bundle quizInfo = getIntent().getExtras();
        if(quizInfo != null) {
            quizID = quizInfo.getString("quizId");
        }

        //Download questions from database
        ql =  Database.getQuestionList(getResources().getString(R.string.getQuizQuestions), quizID);

        //Prints the result
        printResult();

        //Listener for the return button
        View.OnClickListener listAdd = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StatisticsActivity.this, MainActivity.class));
                overridePendingTransition(R.animator.push_right_in,R.animator.push_right_out);
            }
        };

        returnButton.setOnClickListener(listAdd);
    }

    //Prints the result
    private void printResult() {

        String output = "";

        //Iterates through the list of questions
        while(!ql.endOfList()){
            Question temp = ql.getNext();

            //Calculates the percentage of correct answers
            double perc;
            if (temp.getShowTimes() < 1) {
                perc = 0;
            }else{
                perc = (double)temp.getNoCorrectAnswers()/(double)temp.getShowTimes();
            }
            perc = perc*100;
            DecimalFormat df = new DecimalFormat("0.00");

            //The printed string
            output = (output + "\nQuestion: " + temp.getQuestion() + "\nAnswer: " + temp.getAnswer() +  "\nResult: " + df.format(perc) + "%\n");
        }
        resultView.setText(output);
    }


}