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

        //Alex code
        StrictMode.enableDefaults(); //STRICT MODE ENABLED
        getData();

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
        //createQuestion();

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

    public void getData(){
        String result = "";
        InputStream isr = null;
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.ilmkandidat.byethost7.com/getAllCustomers.php"); //YOUR PHP SCRIPT ADDRESS
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            isr = entity.getContent();
        }
        catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
            //resultView.setText("Couldnt connect to database");
        }
        //convert response to string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            isr.close();

            result=sb.toString();
        }
        catch(Exception e){
            Log.e("log_tag", "Error  converting result "+e.toString());
        }

        //parse json data
        try {
            String s = "";
            JSONArray jArray = new JSONArray(result);

            for(int i=0; i<1 /*jArray.length()*/;i++){
                JSONObject json = jArray.getJSONObject(i);
               // s = s +
                        // "Uppgifter : "+json.getString("ID")+" "+json.getString("Name")+"\n";

               //         "Fråga :  "+json.getString("Name")+"\n" + "Rätt svar :  " +json.getString("Correct") +"\n" + "Fel svar :  " +json.getString("Wrong1") +"\n";

                question = new Question();
                question.setQuestion(json.getString("Name"));
                question.setAnswer(json.getString("Correct"));

                try {
                    question.addAlternative(json.getString("Wrong1"));
                    question.addAlternative(json.getString("Wrong2"));
                    question.addAlternative(json.getString("Wrong3"));
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //resultView.setText(s);

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("log_tag", "Error Parsing Data "+e.toString());
        }

    }
}

