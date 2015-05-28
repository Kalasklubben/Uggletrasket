package com.example.johan.uggletrasket;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AddQuestion extends ActionBarActivity {

    EditText editQuestion, editCorrect, editWrong1, editWrong2, editWrong3;
    ImageButton bSubmit, bBack;
    int noQuestions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_add_question);

        editQuestion = (EditText) findViewById(R.id.question);
        editCorrect = (EditText) findViewById(R.id.correct);
        editWrong1 = (EditText) findViewById(R.id.wrong1);
        editWrong2 = (EditText) findViewById(R.id.wrong2);
        editWrong3 = (EditText) findViewById(R.id.wrong3);

        bSubmit = (ImageButton) findViewById(R.id.createButton);
        bBack = (ImageButton) findViewById(R.id.cancelButton);

        View.OnClickListener backList = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddQuestion.this, MainActivity.class));
                overridePendingTransition(R.animator.push_right_in,R.animator.push_right_out);
            }
        };

        bBack.setOnClickListener(backList);
        bSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0){
                String Question = "" + editQuestion.getText().toString();
                String Correct = "" + editCorrect.getText().toString();
                String Wrong1 = "" + editWrong1.getText().toString();
                String Wrong2 = "" + editWrong2.getText().toString();
                String Wrong3 = "" + editWrong3.getText().toString();

                //Get quizInfo from AddQuiz
                String quizName = null;
                String quizId = null;
                Bundle quizInfo = getIntent().getExtras();
                if(quizInfo != null) {
                    quizName = quizInfo.getString("quizName");
                    quizId = quizInfo.getString("quizId");
                }

                //Create Question
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

                nameValuePairs.add(new BasicNameValuePair("Id", UUID.randomUUID().toString()));
                nameValuePairs.add(new BasicNameValuePair("Question", Question));
                nameValuePairs.add(new BasicNameValuePair("Correct", Correct));
                nameValuePairs.add(new BasicNameValuePair("Wrong1", Wrong1));
                nameValuePairs.add(new BasicNameValuePair("Wrong2", Wrong2));
                nameValuePairs.add(new BasicNameValuePair("Wrong3", Wrong3));
                nameValuePairs.add(new BasicNameValuePair("QuizId", quizId));
                nameValuePairs.add(new BasicNameValuePair("Showtime", "0"));
                nameValuePairs.add(new BasicNameValuePair("NoCurrentAnswer", "0"));
                nameValuePairs.add(new BasicNameValuePair("QuizId", quizId));

                //Upload Question
                Database.update(nameValuePairs, getResources().getString(R.string.addQuestion));

                //Go to new "addQuestion" screen, every new question will need the quiz id.
                Intent intent = new Intent(AddQuestion.this, AddQuestion.class);
                intent.putExtra("quizId", quizInfo.getString("quizId"));
                intent.putExtra("quizName", quizInfo.getString("quizName"));
                startActivity(intent);
                overridePendingTransition(R.animator.push_up_in,R.animator.push_up_out);
            }
        });
    }

    private void clearDisplay() {
        editQuestion.setText("");
        editCorrect.setText("");
        editWrong1.setText("");
        editWrong2.setText("");
        editWrong3.setText("");
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
