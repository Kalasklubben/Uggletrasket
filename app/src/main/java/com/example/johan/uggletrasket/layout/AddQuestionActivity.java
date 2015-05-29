package com.example.johan.uggletrasket.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.johan.uggletrasket.util.Database;
import com.example.johan.uggletrasket.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AddQuestionActivity extends ActionBarActivity {

    private EditText editQuestion, editCorrect, editWrong1, editWrong2, editWrong3;
    private ImageButton submitButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_question);

        editQuestion = (EditText) findViewById(R.id.question);
        editCorrect = (EditText) findViewById(R.id.correct);
        editWrong1 = (EditText) findViewById(R.id.wrong1);
        editWrong2 = (EditText) findViewById(R.id.wrong2);
        editWrong3 = (EditText) findViewById(R.id.wrong3);
        submitButton = (ImageButton) findViewById(R.id.createButton);
        backButton = (ImageButton) findViewById(R.id.cancelButton);

        //Listener for back button. Go back to the main activity if back button is clicked
        View.OnClickListener backList = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddQuestionActivity.this, MainActivity.class));
                overridePendingTransition(R.animator.push_right_in,R.animator.push_right_out);
            }
        };

        backButton.setOnClickListener(backList);

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String Question = "" + editQuestion.getText().toString();
                String Correct = "" + editCorrect.getText().toString();
                String Wrong1 = "" + editWrong1.getText().toString();
                String Wrong2 = "" + editWrong2.getText().toString();
                String Wrong3 = "" + editWrong3.getText().toString();

                //Get info from a quiz, sent from AddQuizActivity
                Bundle quizInfo = getIntent().getExtras();

                //Using nameValuePairs to collect input data entered by the user in order to submit it the the database
                List<NameValuePair> nameValuePairs = new ArrayList<>(1);
                nameValuePairs.add(new BasicNameValuePair("Id", UUID.randomUUID().toString()));
                nameValuePairs.add(new BasicNameValuePair("Question", Question));
                nameValuePairs.add(new BasicNameValuePair("Correct", Correct));
                nameValuePairs.add(new BasicNameValuePair("Wrong1", Wrong1));
                nameValuePairs.add(new BasicNameValuePair("Wrong2", Wrong2));
                nameValuePairs.add(new BasicNameValuePair("Wrong3", Wrong3));
                nameValuePairs.add(new BasicNameValuePair("QuizId", quizInfo.getString("quizId")));
                nameValuePairs.add(new BasicNameValuePair("Showtime", "0"));
                nameValuePairs.add(new BasicNameValuePair("NoCurrentAnswer", "0"));
                nameValuePairs.add(new BasicNameValuePair("QuizId", quizInfo.getString("quizId")));

                //Upload question using a specific script
                Database.addTableEntry(nameValuePairs, getResources().getString(R.string.addQuestion));

                //Re-call AddQuestionActivity, every new question will need the quiz information which will be transfered using intent
                Intent intent = new Intent(AddQuestionActivity.this, AddQuestionActivity.class);
                intent.putExtra("quizId", quizInfo.getString("quizId"));
                intent.putExtra("quizName", quizInfo.getString("quizName"));
                startActivity(intent);
                overridePendingTransition(R.animator.push_up_in, R.animator.push_up_out);
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
