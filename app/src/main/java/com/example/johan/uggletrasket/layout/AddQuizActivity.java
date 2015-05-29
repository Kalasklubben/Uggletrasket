package com.example.johan.uggletrasket.layout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.johan.uggletrasket.R;
import com.example.johan.uggletrasket.util.Database;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddQuizActivity extends ActionBarActivity {

    private EditText quizName, password;
    private ImageButton createButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_quiz);

        quizName = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        createButton = (ImageButton) findViewById(R.id.createButton);
        backButton = (ImageButton) findViewById(R.id.cancelButton);

        //Listener. Go back to the main activity if backButton button is clicked
        View.OnClickListener cancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddQuizActivity.this, MainActivity.class));
                overridePendingTransition(R.animator.push_right_in,R.animator.push_right_out);
            }
        };

        backButton.setOnClickListener(cancelListener);

        createButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {

                //Convert entered information into strings.
                String name = "" + quizName.getText().toString();
                String passw = "" + password.getText().toString();

                //Using nameValuePairs to collect input data entered by the user in order to submit it the the database
                List<NameValuePair> nameValuePairs = new ArrayList<>(1);
                String randomId = UUID.randomUUID().toString();
                nameValuePairs.add(new BasicNameValuePair("Id", randomId));
                nameValuePairs.add(new BasicNameValuePair("Name", name));
                nameValuePairs.add(new BasicNameValuePair("Password", passw));

                //Upload Quiz
                Database.addTableEntry(nameValuePairs, getResources().getString(R.string.addQuiz));

                //Enters AddQuestionActivity after a quiz is created
                Intent intent = new Intent(AddQuizActivity.this, AddQuestionActivity.class);
                intent.putExtra("quizId", randomId);
                intent.putExtra("quizName", name);
                startActivity(intent);
                overridePendingTransition(R.animator.push_up_in, R.animator.push_up_out);
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
