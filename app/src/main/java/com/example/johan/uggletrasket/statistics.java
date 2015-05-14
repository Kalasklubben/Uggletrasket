package com.example.johan.uggletrasket;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;


public class Statistics extends Activity {
    /** Called when the activity is first created. */

    TextView resultView;
    ScrollView mScrollView;
    String quizID = "";
    QuestionList ql;
            
;    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        StrictMode.enableDefaults(); //STRICT MODE ENABLED

        resultView= (TextView) findViewById(R.id.result);

        //Get quizID
        Bundle quizInfo = getIntent().getExtras();
        if(quizInfo != null) {
            quizID = quizInfo.getString("quizId");
        }
        
        ql =  LoadQuestions.getData(getResources().getString(R.string.getQuizQuestions), quizID);
        printResult();
        
    }

    private void printResult() {
        String output = "The result of choosen quiz:\n";
        while(!ql.endOfList()){
            Question temp = ql.getNext();
            double perc = (double)temp.getNoCorrectAnswers()/(double)temp.getShowTimes();
            perc = perc*100;
            DecimalFormat df = new DecimalFormat("0.00");
            output = (output + "\nQuestion: " + temp.getQuestion() + "\nAnswer: " + temp.getAnswer() +  "\nResult: " + df.format(perc) + "%\n");
        }
        resultView.setText(output);
    }


}