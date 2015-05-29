package com.example.johan.uggletrasket;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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

/**
 * Created by Bangen on 15-05-09.
 */
public class AddQuizActivity extends ActionBarActivity {

    //Declaration of all inputs and buttons
    private EditText quizName, password;
    private ImageButton create, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

        //Connect activity with layout
        setContentView(R.layout.activity_add_quiz);

        //Connect all inputs and buttons with respective layout id
        quizName = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        create = (ImageButton) findViewById(R.id.createButton);
        cancel = (ImageButton) findViewById(R.id.cancelButton);

        //Listener. Go back to the main activity if cancel button is clicked
        View.OnClickListener cancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddQuizActivity.this, MainActivity.class));
                overridePendingTransition(R.animator.push_right_in,R.animator.push_right_out);
            }
        };

        //Add listener to cancel button
        cancel.setOnClickListener(cancelListener);

        //Listener. Add quiz when create button is clicked. Then navigate to AddQuestion activity.
        create.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View arg0){

                //Convert entered information into strings.
                String name = "" + quizName.getText().toString();
                String passw = "" + password.getText().toString();

                //Create quiz
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

                //Saving the randomId for later use.
                String randomId = UUID.randomUUID().toString();

                nameValuePairs.add(new BasicNameValuePair("Id", randomId));
                nameValuePairs.add(new BasicNameValuePair("Name", name));
                nameValuePairs.add(new BasicNameValuePair("Password", passw));

                //Upload Quiz
                Database.update(nameValuePairs,getResources().getString(R.string.addQuiz));

                //After a new quiz is created, go to AddQuestions. Added questions will need the quizId.
                Intent intent = new Intent(AddQuizActivity.this, AddQuestionActivity.class);
                intent.putExtra("quizId", randomId);
                intent.putExtra("quizName", name);
                startActivity(intent);
                overridePendingTransition(R.animator.push_up_in,R.animator.push_up_out);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_question, menu);
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

    //Navigate to the main menu when pressing the back button.
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
