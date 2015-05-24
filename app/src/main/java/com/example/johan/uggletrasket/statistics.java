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


public class Statistics extends Activity {
    /** Called when the activity is first created. */

    TextView resultView;
    TextView header;
    private ImageButton returnButton;
    ScrollView mScrollView;
    String quizID = "";
    QuestionList ql;
            
;    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        StrictMode.enableDefaults(); //STRICT MODE ENABLED

        resultView = (TextView) findViewById(R.id.result);
        header = (TextView) findViewById(R.id.header);
        returnButton = (ImageButton) findViewById(R.id.return_stats_button);

        //Get quizID
        Bundle quizInfo = getIntent().getExtras();
        if(quizInfo != null) {
            quizID = quizInfo.getString("quizId");
        }
        
        ql =  LoadQuestions.getData(getResources().getString(R.string.getQuizQuestions), quizID);
        printResult();

        View.OnClickListener listAdd = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Statistics.this, MainActivity.class));
            }
        };

        returnButton.setOnClickListener(listAdd);
        
    }

    private void printResult() {
        //String output = "The result of choosen quiz:\n";
        String output = "";
        while(!ql.endOfList()){
            Question temp = ql.getNext();
            double perc;
            if (temp.getShowTimes() < 1) {
                perc = 0;
            }else{
                perc = (double)temp.getNoCorrectAnswers()/(double)temp.getShowTimes();
            }

            perc = perc*100;
            DecimalFormat df = new DecimalFormat("0.00");
            output = (output + "\nQuestion: " + temp.getQuestion() + "\nAnswer: " + temp.getAnswer() +  "\nResult: " + df.format(perc) + "%\n");
            //output = "Question: " + temp.getQuestion() + "\nAnswer: " + temp.getAnswer() +  "\nResult: " + df.format(perc) + "%\n";
        }
        resultView.setText(output);
    }


}